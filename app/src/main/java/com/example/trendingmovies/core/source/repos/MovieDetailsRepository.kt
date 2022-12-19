package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.MovieDetailsDao
import com.example.trendingmovies.core.source.local.models.MovieDetailsEntity
import com.example.trendingmovies.core.source.remote.MoviesApi
import com.example.trendingmovies.utils.toMovieDetailsEntity
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDetailsDao: MovieDetailsDao
) : MovieDetailsRepo {

    override suspend fun getMovieDetails(movieId: String): MovieDetailsEntity {
        val movieDetailsEntity = movieDetailsDao.getMovieById(movieId)
        return if (movieDetailsEntity != null) {
            movieDetailsEntity
        } else {
            val webResult = moviesApi.getMovieDetails(movieId)
            val movie = webResult.toMovieDetailsEntity()
            movieDetailsDao.insertMovieDetails(movie)
            movie
        }
    }

    override suspend fun saveMovieDetails(movieDetailsEntity: MovieDetailsEntity): Boolean {
        return true
    }
}