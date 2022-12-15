package com.example.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.utils.toTrendingMovieDtoList
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

    private val moviesMutableLiveData: MutableLiveData<List<TrendingMoviesDto>> = MutableLiveData()
    val moviesLiveData: LiveData<List<TrendingMoviesDto>> = moviesMutableLiveData

    fun getAllMovies() {
        viewModelScope.launch(ioDispatcher) {
            val moviesResult = moviesListRepo.getAllMoviesSync()
            val configurationResult = configurationRepo.getConfiguration()

            val movies = configurationResult toTrendingMovieDtoList moviesResult
            moviesMutableLiveData.postValue(movies)
        }
    }
}