package com.example.trendingmovies.utils

import com.example.trendingmovies.TrendingMoviesDto
import com.example.trendingmovies.database.ConfigurationEntity
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.network.ConfigurationResponse
import com.example.trendingmovies.network.Movie
import com.example.trendingmovies.network.MoviesResponse

fun MoviesResponse.toTrendingMoviesEntityList(): List<TrendingMoviesEntity> {
    val trendingMoviesEntities: MutableList<TrendingMoviesEntity> =
        mutableListOf<TrendingMoviesEntity>()
    this.results.forEach { trendingMoviesEntities.add(it.convertToEntity()) }
    return trendingMoviesEntities
}

private fun Movie.convertToEntity(): TrendingMoviesEntity {
    return TrendingMoviesEntity(
        id = id,
        posterPath = posterPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate,
        genreIds = genreIds.toList(),
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        title = title,
        backdropPath = backdropPath,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        voteAverage = voteAverage
    )
}

fun ConfigurationResponse.toConfigurationEntity(): ConfigurationEntity {
    return ConfigurationEntity(
        baseUrl = this.images.baseUrl,
        secureBaseUrl = this.images.secureBaseUrl,
        backdropSizes = this.images.backdropSizes,
        logoSizes = this.images.logoSizes,
        posterSizes = this.images.posterSizes,
        profileSizes = this.images.profileSizes,
        stillSizes = this.images.stillSizes,
        changeKeys = this.changeKeys
    )
}

infix fun ConfigurationEntity.toTrendingMovieDtoList(movies: List<TrendingMoviesEntity>): List<TrendingMoviesDto> {
    val trendingMoviesDtoList: MutableList<TrendingMoviesDto> = mutableListOf()
    movies.forEach { movie ->
        val posterUrl: String? = movie.posterPath?.let {
            getFullUrl(
                movie.posterPath,
                this.secureBaseUrl,
                this.posterSizes
            )
        }

        val trendingMoviesDto = TrendingMoviesDto(
            id = movie.id.toString(),
            title = movie.title,
            releaseDate = movie.releaseDate,
            voteCount = movie.voteCount.toString(),
            rating = movie.voteAverage.toString(),
            posterUrl = posterUrl
        )

        trendingMoviesDtoList.add(trendingMoviesDto)

    }

    return trendingMoviesDtoList
}

fun getFullUrl(posterPath: String, secureBaseUrl: String, posterSizes: List<String>): String {
    val size: String = posterSizes.firstOrNull { it == "w185" } ?: "original"
    return "$secureBaseUrl$size$posterPath"
}
