package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.MovieDetailsDao
import com.example.trendingmovies.core.source.local.models.MovieDetailsEntity
import com.example.trendingmovies.core.source.remote.MoviesApi
import com.example.trendingmovies.core.source.remote.MoviesRemoteDataSource
import com.example.trendingmovies.utils.toMovieDetailsEntity
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val movieDetailsDao: MovieDetailsDao
) : MovieDetailsRepo {

    override suspend fun getMovieDetails(movieId: String): MovieDetailsEntity? {
        val movieDetailsEntity = movieDetailsDao.getMovieById(movieId)
        return if (movieDetailsEntity != null) {
            movieDetailsEntity
        } else {
            val webResult = moviesRemoteDataSource.getMovieDetails(movieId)
            val movie = webResult?.toMovieDetailsEntity()
            movie?.let { movieDetailsDao.insertMovieDetails(it) }
            movie
        }
    }

    override suspend fun saveMovieDetails(movieDetailsEntity: MovieDetailsEntity): Boolean {
        return true
    }
}