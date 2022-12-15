package com.example.trendingmovies

interface MovieDetailsRepo {
    suspend fun getMovieDetails()
}