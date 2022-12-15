package com.example.trendingmovies

import com.example.trendingmovies.database.TrendingMoviesDao
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.network.MoviesApi
import com.example.trendingmovies.utils.toTrendingMoviesEntityList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesListRepository @Inject constructor(
    private val trendingMoviesDao: TrendingMoviesDao,
    private val moviesApi: MoviesApi
) : MoviesListRepo {

    override suspend fun getAllMoviesSync(): List<TrendingMoviesEntity> {
        val result = moviesApi.getTrendingMovies()
        trendingMoviesDao.insertMovies(result.toTrendingMoviesEntityList())
        return trendingMoviesDao.getAllMoviesSync()
    }
}