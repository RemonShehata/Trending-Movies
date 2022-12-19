package com.example.trendingmovies.core.models

import com.example.trendingmovies.core.source.local.models.Status

data class MovieDetailsDto(
//    val id: Int,
//    val adult: Boolean,
//    val backdropPath: String?,
    val budget: Int,
    val genres: List<String>,
//    val homePage: String?,
//    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String?,
//    val productionCompanies: List<ProductionCompany>,
//    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: String,
    val runtime: Int?,
    val spokenLanguages: List<String>,
    val status: Status,
    val tagline: String?,
    val title: String,
//    val video: Boolean,
    val voteAverage: String,
    val voteCount: String
)