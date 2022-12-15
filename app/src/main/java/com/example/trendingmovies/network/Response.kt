package com.example.trendingmovies.network

import com.squareup.moshi.Json

sealed class Response {
//    object Loading : Response()
//    data class Success(val data: List<Article>) : Response()
//    data class Failure(val reason: FailureReason) : Response()
}

sealed class FailureReason {
    object NoInternet : FailureReason()
    class UnknownError(val error: String) : FailureReason()
}

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int?,
    val total_pages: Int?
)

data class Movie(
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "genre_ids")
    val genreIds: IntArray, // instead of Array<Int> https://kotlinlang.org/docs/arrays.html#primitive-type-arrays
    @Json(name = "id")
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (posterPath != other.posterPath) return false
        if (adult != other.adult) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (id != other.id) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (backdropPath != other.backdropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = posterPath?.hashCode() ?: 0
        result = 31 * result + adult.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + id
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        return result
    }
}

data class MovieDetailsResponse(
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "belongs_to_collection") val belongsToCollection: Any?, //TODO: null or object
    @Json(name = "budget") val budget: Int,
    @Json(name = "genres") val genreResponses: List<GenreResponse>,
    @Json(name = "homepage") val homePage: String?,
    @Json(name = "id") val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyResponse>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountryResponse>,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "revenue") val revenue: Int,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguageResponses: List<SpokenLanguageResponse>,
    @Json(name = "status") val status: StatusResponse,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "title") val title: String,
    @Json(name = "video") val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)

data class GenreResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
)

data class ProductionCompanyResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String,
)

data class ProductionCountryResponse(
    @Json(name = "iso_3166_1") val iso_3166_1: String,
    @Json(name = "name") val name: String,
)

data class SpokenLanguageResponse(
    @Json(name = "iso_639_1") val iso_639_1: String,
    @Json(name = "name") val name: String,
)


enum class StatusResponse(val value: String) {
    Rumored("Rumored"),
    Planned("Planned"),
    InProduction("In Production"),
    PostProduction("Post Production"),
    Released("Released"),
    Canceled("Canceled")

    // TODO: associate with map here
}

data class ConfigurationResponse(
    @Json(name = "images") val images: Image,
    @Json(name = "change_keys") val changeKeys: List<String>,
)

data class Image(
    @Json(name = "base_url") val baseUrl: String,
    @Json(name = "secure_base_url") val secureBaseUrl: String,
    @Json(name = "backdrop_sizes") val backdropSizes: List<String>,
    @Json(name = "logo_sizes") val logoSizes: List<String>,
    @Json(name = "poster_sizes") val posterSizes: List<String>,
    @Json(name = "profile_sizes") val profileSizes: List<String>,
    @Json(name = "still_sizes") val stillSizes: List<String>,

    )
