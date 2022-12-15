package com.example.trendingmovies

import com.example.trendingmovies.database.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

interface TrendingMoviesRepo {
    suspend fun getAllMoviesSync()
    fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>>
    suspend fun getMoviesForPage(): Boolean
}