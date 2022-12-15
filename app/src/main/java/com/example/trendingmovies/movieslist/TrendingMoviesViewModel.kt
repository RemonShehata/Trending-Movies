package com.example.trendingmovies.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.ConfigurationRepo
import com.example.trendingmovies.TAG
import com.example.trendingmovies.TrendingMoviesRepo
import com.example.trendingmovies.TrendingMoviesDto
import com.example.trendingmovies.utils.toTrendingMovieDtoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val trendingMoviesRepo: TrendingMoviesRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val moviesMutableLiveData: MutableLiveData<List<TrendingMoviesDto>> = MutableLiveData()
    val moviesLiveData: LiveData<List<TrendingMoviesDto>> = moviesMutableLiveData
    
    init {
        viewModelScope.launch(ioDispatcher) {
            trendingMoviesRepo.getAllMoviesFlow().collect { moviesList ->
                val configurationResult = configurationRepo.getConfiguration()

                val movies = configurationResult toTrendingMovieDtoList moviesList
                moviesMutableLiveData.postValue(movies)
            }
        }


        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "viewModel init: getting movies from api")
            trendingMoviesRepo.getAllMoviesSync()
        }
    }
}