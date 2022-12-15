package com.example.trendingmovies

import com.example.trendingmovies.database.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

interface TrendingMoviesRepo {
    suspend fun getAllMoviesSync(): List<TrendingMoviesEntity>
    fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>>
}