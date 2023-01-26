package com.example.trendingmovies.core.source.remote

import com.example.trendingmovies.core.source.remote.models.ConfigurationResponse
import com.example.trendingmovies.core.source.remote.models.MovieDetailsResponse
import com.example.trendingmovies.core.source.remote.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApi {

    @GET("discover/movie")
    suspend fun getTrendingMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailsResponse

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}
