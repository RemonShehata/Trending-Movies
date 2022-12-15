package com.example.trendingmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesListRepo: MoviesListRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getAllMovies() {
        viewModelScope.launch(ioDispatcher) {
            val result = moviesListRepo.getAllMoviesSync()
        }
    }
}