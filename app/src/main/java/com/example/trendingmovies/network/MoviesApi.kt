package com.example.trendingmovies.network

import retrofit2.http.GET
import retrofit2.http.Path


interface MoviesApi {

    @GET("discover/movie")
    suspend fun getTrendingMovies(): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetails

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}
