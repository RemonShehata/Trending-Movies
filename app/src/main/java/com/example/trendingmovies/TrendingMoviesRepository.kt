package com.example.trendingmovies

import com.example.trendingmovies.database.TrendingMoviesDao
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.database.TrendingMoviesPageDao
import com.example.trendingmovies.database.TrendingMoviesPageEntity
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
        val result = moviesApi.getTrendingMovies()
        trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
        trendingMoviesPageDao.insertPage(result.toTrendingMoviesPageEntity())
    }


//    override suspend fun getAllMoviesSync(){
//        val result = moviesApi.getTrendingMovies()
//        trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
//        trendingMoviesPageDao.insertPage(result.toTrendingMoviesPageEntity())
//    }

    override fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>> {
        return trendingMoviesDao.getAllMoviesFlow()
    }

    override suspend fun getMoviesForPage(): Boolean {
        val pageData = trendingMoviesPageDao.getPageSync()
        var currentPage = pageData.page
        if (pageData.totalPages != null && pageData.page < pageData.totalPages){
            // we didn't reach the end yet, get the next page
            val result = moviesApi.getTrendingMovies(page = ++currentPage)

            trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
            trendingMoviesPageDao.insertPage(result.toTrendingMoviesPageEntity())
            return true
        } else {
            return false
        }
    }
}