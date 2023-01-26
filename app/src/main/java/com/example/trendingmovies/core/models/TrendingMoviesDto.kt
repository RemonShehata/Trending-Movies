package com.example.trendingmovies.core.models

data class TrendingMoviesDto(
    val id: String,
    val title: String,
    val releaseDate: String,
    val voteCount: String,
    val rating: String,
    val posterUrl: String?
)