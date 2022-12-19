package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.models.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

interface TrendingMoviesRepo {
    suspend fun getAllMoviesSync()
    fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>>
    suspend fun getMoviesForPage()
}