package com.example.trendingmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MovieDetailsEntity(
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "belongs_to_collection") val belongsToCollection: Any?, //TODO: null or object
    @ColumnInfo(name = "budget") val budget: Int,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "homepage") val homePage: String?,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "imdb_id") val imdbId: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
//    @ColumnInfo(name = "production_companies") val productionCompanies: List<ProductionCompany>,
//    @ColumnInfo(name = "production_countries") val productionCountries: List<ProductionCountry>,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "revenue") val revenue: Int,
    @ColumnInfo(name = "runtime") val runtime: Int?,
    @ColumnInfo(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @ColumnInfo(name = "status") val status: Status,
    @ColumnInfo(name = "tagline") val tagline: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String,
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String,
)

enum class Status(val value: String) {
    Rumored("Rumored"),
    Planned("Planned"),
    InProduction("In Production"),
    PostProduction("Post Production"),
    Released("Released"),
    Canceled("Canceled")

    // TODO: associate with map here
}