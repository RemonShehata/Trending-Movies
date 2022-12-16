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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val trendingMoviesRepo: TrendingMoviesRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val moviesMutableLiveData: MutableLiveData<TrendingResult> = MutableLiveData()
    val moviesLiveData: LiveData<TrendingResult> = moviesMutableLiveData

    fun getNextPageData() {
        moviesMutableLiveData.value = TrendingResult.Loading
        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "getNextPageData in viewModel ")
            trendingMoviesRepo.getMoviesForPage() //returns false if we reached the end
        }
    }

    init {
        Log.d(TAG, "viewModel: init")
        moviesMutableLiveData.value = TrendingResult.Loading

        viewModelScope.launch(ioDispatcher) {
            trendingMoviesRepo.getAllMoviesFlow().collect { moviesList ->
                Log.d(TAG, "viewModel: collect - size = ${moviesList.size}")
                val configurationResult = configurationRepo.getConfiguration()

                val movies = configurationResult toTrendingMovieDtoList moviesList
                moviesMutableLiveData.postValue(TrendingResult.Success(movies))
            }
        }


        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "viewModel init: getting movies from api")
            trendingMoviesRepo.getAllMoviesSync()
        }
    }
}

sealed class TrendingResult {
    object Loading : TrendingResult()
    data class Success(val movies: List<TrendingMoviesDto>) : TrendingResult()
    data class Error(val errorType: ErrorType): TrendingResult()
}

sealed class ErrorType{
    object NoInternet: ErrorType()
    object ReachedEndOfList: ErrorType()
}