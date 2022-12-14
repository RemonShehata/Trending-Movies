package com.example.trendingmovies

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.databinding.ActivityMainBinding
import com.example.trendingmovies.network.Movie
import com.example.trendingmovies.network.MoviesApi
import com.example.trendingmovies.network.MoviesResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var moviesApi: MoviesApi

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    suspend fun getAllMovies() {
        val result = moviesApi.getTrendingMovies()
//    Log.d(TAG, "getAllMovies: $result")
        moviesDatabase.trendingMoviesDao().insertMovies(result.toTrendingMoviesEntityList())
    }

    suspend fun getMovieDetails() {
        val result = moviesApi.getMovieDetails(MOVIE_ID)
//    Log.d(TAG, "getMovieDetails: $result")
    }

    suspend fun getConfigurations() {
        val result = moviesApi.getConfiguration()
//    Log.d(TAG, "getConfigurations: $result")
    }
}

const val TAG = "Remon"
const val MOVIE_ID = "436270"

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