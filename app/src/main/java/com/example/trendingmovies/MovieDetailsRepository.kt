package com.example.trendingmovies

import com.example.trendingmovies.database.MovieDetailsDao
import com.example.trendingmovies.network.MoviesApi
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    moviesApi: MoviesApi,
    movieDetailsDao: MovieDetailsDao
) : MovieDetailsRepo {

    override suspend fun getMovieDetails() {
        TODO("Not yet implemented")
    }
}