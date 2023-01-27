package com.example.trendingmovies.core.source.remote

import com.example.trendingmovies.core.source.remote.executor.RemoteExecutor
import com.example.trendingmovies.core.source.remote.models.ConfigurationResponse
import com.example.trendingmovies.core.source.remote.models.MovieDetailsResponse
import com.example.trendingmovies.core.source.remote.models.MoviesResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesApi: MoviesApi) :
    RemoteExecutor() {

    suspend fun getTrendingMovies(page: Int = 1): MoviesResponse? {
        return executeApiCall { moviesApi.getTrendingMovies(page) }
    }

    suspend fun getMovieDetails(movieId: String): MovieDetailsResponse? {
        return executeApiCall { moviesApi.getMovieDetails(movieId) }
    }

    suspend fun getConfiguration(): ConfigurationResponse? {
        return executeApiCall { moviesApi.getConfiguration() }
    }
}