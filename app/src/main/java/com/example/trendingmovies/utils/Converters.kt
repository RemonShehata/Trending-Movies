package com.example.trendingmovies.utils

import com.example.trendingmovies.MovieDetailsDto
import com.example.trendingmovies.TrendingMoviesDto
import com.example.trendingmovies.database.*
import com.example.trendingmovies.network.*

fun MoviesResponse.toTrendingMoviesEntityList(): List<TrendingMoviesEntity> {
    val trendingMoviesEntities: MutableList<TrendingMoviesEntity> =
        mutableListOf<TrendingMoviesEntity>()
    this.results.forEach { trendingMoviesEntities.add(it.convertToEntity()) }
    return trendingMoviesEntities
}

fun MoviesResponse.toTrendingMoviesPageEntity(): TrendingMoviesPageEntity {
    return TrendingMoviesPageEntity(
        currentPage = this.page,
        totalPages = this.totalPages
    )
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
        voteAverage = voteAverage,
        System.currentTimeMillis()
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

infix fun ConfigurationEntity.toMovieDetailsDto(movie: MovieDetailsEntity): MovieDetailsDto {
    val posterUrl: String? = movie.posterPath?.let {
        getFullUrl(
            movie.posterPath,
            this.secureBaseUrl,
            this.posterSizes
        )
    }

    return MovieDetailsDto(
        budget = movie.budget,
        genres = movie.genres.map { it.name },
        originalLanguage = movie.originalLanguage,
        originalTitle = movie.originalTitle,
        overview = movie.overview,
        popularity = movie.popularity,
        posterUrl = posterUrl,
        releaseDate = movie.releaseDate,
        revenue = movie.revenue.toString(),
        runtime = movie.runtime,
        spokenLanguages = movie.spokenLanguages.map { it.name },
        status = movie.status,
        tagline = movie.tagline,
        title = movie.title,
        voteAverage = movie.voteAverage.toString(),
        voteCount = movie.voteCount.toString()
    )
}

fun getFullUrl(posterPath: String, secureBaseUrl: String, posterSizes: List<String>): String {
    val size: String = posterSizes.firstOrNull { it == "w185" } ?: "original"
    return "$secureBaseUrl$size$posterPath"
}

fun MovieDetailsResponse.toMovieDetailsEntity(): MovieDetailsEntity {
    val genreList: MutableList<Genre> = mutableListOf()
    val productionCompaniesList: MutableList<ProductionCompany> = mutableListOf()
    val productionCountriesList: MutableList<ProductionCountry> = mutableListOf()
    val spokenLanguagesList: MutableList<SpokenLanguage> = mutableListOf()

    this.genreResponses.forEach { genreList.add(it.toGenre()) }
    this.productionCompanies.forEach { productionCompaniesList.add(it.toProductionCompany()) }
    this.productionCountries.forEach { productionCountriesList.add(it.toProductionCompany()) }
    this.spokenLanguageResponses.forEach { spokenLanguagesList.add(it.toSpokenLanguage()) }

    return MovieDetailsEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = backdropPath,
        budget = this.budget,
        genres = genreList,
        homePage = this.homePage,
        imdbId = this.imdbId,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = productionCompaniesList,
        productionCountries = productionCountriesList,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = spokenLanguagesList,
        status = this.status.toStatus(),
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

private fun StatusResponse.toStatus(): Status {
    return when (this) {
        StatusResponse.Rumored -> Status.Rumored
        StatusResponse.Planned -> Status.Planned
        StatusResponse.InProduction -> Status.InProduction
        StatusResponse.PostProduction -> Status.PostProduction
        StatusResponse.Released -> Status.Released
        StatusResponse.Canceled -> Status.Canceled
    }
}

private fun SpokenLanguageResponse.toSpokenLanguage(): SpokenLanguage {
    return SpokenLanguage(
        iso_639_1 = this.iso_639_1,
        name = this.name
    )
}

private fun ProductionCountryResponse.toProductionCompany(): ProductionCountry {
    return ProductionCountry(
        iso_3166_1 = this.iso_3166_1,
        name = this.name
    )
}

private fun ProductionCompanyResponse.toProductionCompany(): ProductionCompany {
    return ProductionCompany(
        id = this.id,
        name = this.name,
        logoPath = this.logoPath,
        originCountry = this.originCountry
    )
}

private fun GenreResponse.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
