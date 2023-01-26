package com.example.trendingmovies.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.trendingmovies.R
import com.example.trendingmovies.core.source.local.MoviesDatabase
import com.example.trendingmovies.databinding.ActivityMainBinding
import com.example.trendingmovies.core.source.remote.MoviesApi
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

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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

