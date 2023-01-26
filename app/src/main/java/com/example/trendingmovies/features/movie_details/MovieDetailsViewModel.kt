package com.example.trendingmovies.features.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.core.models.ErrorType
import com.example.trendingmovies.core.models.State
import com.example.trendingmovies.core.models.MovieDetailsDto
import com.example.trendingmovies.core.source.repos.ConfigurationRepo
import com.example.trendingmovies.core.source.repos.MovieDetailsRepo
import com.example.trendingmovies.utils.toMovieDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepo: MovieDetailsRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val movieDetailsMutableLiveData: MutableLiveData<State<MovieDetailsDto>> =
        MutableLiveData()
    val movieDetailsLiveData: LiveData<State<MovieDetailsDto>> = movieDetailsMutableLiveData

    fun getMovieDetails(movieId: String) {
        movieDetailsMutableLiveData.value = State.Loading

        viewModelScope.launch(ioDispatcher) {
            networkCallWithExceptionHandling {
                val result = movieDetailsRepo.getMovieDetails(movieId)
                val config = configurationRepo.getConfiguration()
                val movie = config toMovieDetailsDto result
                movieDetailsMutableLiveData.postValue(State.Success(movie))
            }
        }
    }


    private suspend fun networkCallWithExceptionHandling(call: suspend () -> Unit) {
        try {
            call.invoke()
        } catch (unknownHostException: UnknownHostException) {
            movieDetailsMutableLiveData.postValue(State.Error(ErrorType.NoInternet))
        } catch (socketTimeoutException: SocketTimeoutException) {
            movieDetailsMutableLiveData.postValue(State.Error(ErrorType.NoInternet))
        } catch (exception: Exception) {
            movieDetailsMutableLiveData.postValue(State.Error(ErrorType.UnknownError))
        }
    }
}