package com.example.trendingmovies.core.source.repos

import android.util.Log
import com.example.trendingmovies.core.source.local.TrendingMoviesDao
import com.example.trendingmovies.core.source.local.TrendingMoviesPageDao
import com.example.trendingmovies.core.source.local.models.TrendingMoviesEntity
import com.example.trendingmovies.core.source.remote.MoviesRemoteDataSource
import com.example.trendingmovies.utils.toTrendingMoviesEntityList
import com.example.trendingmovies.utils.toTrendingMoviesPageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingMoviesRepository @Inject constructor(
    private val trendingMoviesDao: TrendingMoviesDao,
    private val trendingMoviesPageDao: TrendingMoviesPageDao,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : TrendingMoviesRepo {

    override suspend fun getAllMoviesSync(){
        if (trendingMoviesDao.getAllMoviesSync().isEmpty()){
            val result = moviesRemoteDataSource.getTrendingMovies()
            result?.let { trendingMoviesDao.insertMovies(it.toTrendingMoviesEntityList()) }
            result?.let { trendingMoviesPageDao.insertPage(it.toTrendingMoviesPageEntity()) }
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

        var currentPage = pageData.currentPage

        if (pageData.totalPages != null && currentPage < pageData.totalPages) {
            // we didn't reach the end yet, get the next page
            Log.d(TAG, "getting data for page: ${++currentPage}")
            val result = moviesRemoteDataSource.getTrendingMovies(page = ++currentPage)

            result?.toTrendingMoviesEntityList()?.let { trendingMoviesDao.insertMovies(it) }

            result?.toTrendingMoviesPageEntity()?.let { trendingMoviesPageDao.insertPage(it) }

        } else {
            Log.w(TAG, "can not get movies data for next page. " +
                    "total pages: ${pageData.totalPages} " +
                    "current page: $currentPage")
        }
    }

    companion object{
        private const val TAG = "TrendingMoviesRepository"
    }
}