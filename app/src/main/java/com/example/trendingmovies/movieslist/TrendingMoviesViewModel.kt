package com.example.trendingmovies.movieslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.*
import com.example.trendingmovies.utils.toTrendingMovieDtoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val trendingMoviesRepo: TrendingMoviesRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val moviesMutableLiveData: MutableLiveData<State<List<TrendingMoviesDto>>> =
        MutableLiveData()

    val moviesLiveData: LiveData<State<List<TrendingMoviesDto>>> = moviesMutableLiveData

    private val isOnlineMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val isOnlineLiveData: LiveData<Boolean> = isOnlineMutableLiveData
    fun getNextPageData() {
        moviesMutableLiveData.value = State.Loading
        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "getNextPageData in viewModel ")
            trendingMoviesRepo.getMoviesForPage() //returns false if we reached the end
        }
    }

    init {
        Log.d(TAG, "viewModel: init")
        moviesMutableLiveData.value = State.Loading
        isOnlineMutableLiveData.value = when (networkStateMonitor.getNetworkState()) {
            NetworkState.Connected -> true
            NetworkState.Disconnected -> false
        }

        viewModelScope.launch(ioDispatcher) {
            trendingMoviesRepo.getAllMoviesFlow().collect { moviesList ->
                Log.d(TAG, "viewModel: collect - size = ${moviesList.size}")
                try {
                    val configurationResult = configurationRepo.getConfiguration()
                    val movies = configurationResult toTrendingMovieDtoList moviesList
                    moviesMutableLiveData.postValue(State.Success(movies))
                } catch (unknownHostException: UnknownHostException) {
                    Log.d(TAG, "caught exception in collect")
                    moviesMutableLiveData.postValue(State.Error(ErrorType.NoInternet))
                } catch (exception: Exception) {
                    moviesMutableLiveData.postValue(State.Error(ErrorType.UnknownError))
                }
            }
        }


        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "viewModel init: getting movies from api")
            try {
                trendingMoviesRepo.getAllMoviesSync()
            } catch (unknownHostException: UnknownHostException) {
                moviesMutableLiveData.postValue(State.Error(ErrorType.NoInternet))
            } catch (exception: Exception) {
                moviesMutableLiveData.postValue(State.Error(ErrorType.UnknownError))
            }
        }
    }
}

