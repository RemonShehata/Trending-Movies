package com.example.trendingmovies.core.models

import com.example.trendingmovies.core.source.local.models.Status

data class MovieDetailsDto(
    val budget: Int,
    val genres: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val spokenLanguages: List<String>,
    val status: Status,
    val tagline: String?,
    val title: String,
    val voteAverage: String,
    val voteCount: String
)