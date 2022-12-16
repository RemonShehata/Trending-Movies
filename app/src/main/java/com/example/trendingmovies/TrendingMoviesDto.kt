package com.example.trendingmovies

data class TrendingMoviesDto( //TODO: remove field we don't need here
    val id: String,
    val title: String,
    val releaseDate: String,
    val voteCount: String,
    val rating: String,
    val posterUrl: String?
)