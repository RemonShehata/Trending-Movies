package com.example.trendingmovies

import android.util.Log
import com.example.trendingmovies.core.TAG
import com.example.trendingmovies.database.TrendingMoviesDao
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.database.TrendingMoviesPageDao
import com.example.trendingmovies.network.MoviesApi
import com.example.trendingmovies.utils.toTrendingMoviesEntityList
import com.example.trendingmovies.utils.toTrendingMoviesPageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingMoviesRepository @Inject constructor(
    private val trendingMoviesDao: TrendingMoviesDao,
    private val trendingMoviesPageDao: TrendingMoviesPageDao,
    private val moviesApi: MoviesApi
) : TrendingMoviesRepo {

    override suspend fun getAllMoviesSync(){
        if (trendingMoviesDao.getAllMoviesSync().isEmpty()){
            val result = moviesApi.getTrendingMovies()
            trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
            trendingMoviesPageDao.insertPage(result.toTrendingMoviesPageEntity())
        }
    }

    override fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>> {
        return trendingMoviesDao.getAllMoviesFlow()
    }

    override suspend fun getMoviesForPage() {
        // if we don't have data for page then we are trying to get the first page after the device
        // was offline then we got connectivity but
        val pageData = trendingMoviesPageDao.getPageSync() ?: kotlin.run {
            getAllMoviesSync()
            trendingMoviesPageDao.getPageSync()!! // we are sure it can't be null here,
            // the previous
        }

        var currentPage = pageData.currentPage ?: 1
        Log.d(TAG, "getMoviesForPage: before")
        Log.d(TAG, "getMoviesForPage: currentPage: $currentPage")
        Log.d(TAG, "getMoviesForPage: totalPages: ${pageData.totalPages}")
        Log.d(TAG, "getMoviesForPage:============")
        if (pageData.totalPages != null && currentPage < pageData.totalPages) {
            // we didn't reach the end yet, get the next page
            val result = moviesApi.getTrendingMovies(page = ++currentPage)
            Log.d(TAG, "getMoviesForPage: after")
            Log.d(TAG, "getMoviesForPage: currentPage: $currentPage")
            Log.d(TAG, "getMoviesForPage: totalPages: ${pageData.totalPages}")

            trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
            Log.d(TAG, "getMoviesForPage: ${result.page}")
            trendingMoviesPageDao.insertPage(result.toTrendingMoviesPageEntity())
            Log.d(TAG, "getMoviesForPage: ${result.toTrendingMoviesPageEntity()}")
        } else {
            Log.d(TAG, "getMoviesForPage: inside else")
        }
    }
}