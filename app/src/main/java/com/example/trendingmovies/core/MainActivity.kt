package com.example.trendingmovies.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.databinding.ActivityMainBinding
import com.example.trendingmovies.network.MoviesApi
import com.example.trendingmovies.utils.toTrendingMoviesEntityList
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

