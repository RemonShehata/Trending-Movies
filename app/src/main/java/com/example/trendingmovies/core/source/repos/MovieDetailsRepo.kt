package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.models.MovieDetailsEntity

interface MovieDetailsRepo {
    suspend fun getMovieDetails(movieId: String): MovieDetailsEntity?
    suspend fun saveMovieDetails(movieDetailsEntity: MovieDetailsEntity): Boolean
}