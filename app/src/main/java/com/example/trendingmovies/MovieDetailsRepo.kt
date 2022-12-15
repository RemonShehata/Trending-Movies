package com.example.trendingmovies

import com.example.trendingmovies.database.MovieDetailsEntity

interface MovieDetailsRepo {
    suspend fun getMovieDetails(movieId: String): MovieDetailsEntity
    suspend fun saveMovieDetails(movieDetailsEntity: MovieDetailsEntity): Boolean
}