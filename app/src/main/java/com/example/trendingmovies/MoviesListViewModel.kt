package com.example.trendingmovies

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.database.TrendingMoviesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repo: MoviesListRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableTrendingMoviesState:  MutableState<List<TrendingMoviesEntity>> = mutableStateOf(emptyList())
    val trendingMoviesState: State<List<TrendingMoviesEntity>> = mutableTrendingMoviesState

    fun getAllMovies() {
        viewModelScope.launch(ioDispatcher) {
            val result = repo.getAllMoviesSync()
            mutableTrendingMoviesState.value = result
        }
    }
}