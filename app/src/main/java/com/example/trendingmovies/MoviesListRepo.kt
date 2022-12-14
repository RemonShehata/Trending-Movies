package com.example.trendingmovies

import com.example.trendingmovies.database.TrendingMoviesEntity

interface MoviesListRepo {
    suspend fun getAllMoviesSync(): List<TrendingMoviesEntity>
//    fun getAllMoviesFlow(): List<TrendingMoviesEntity>
}