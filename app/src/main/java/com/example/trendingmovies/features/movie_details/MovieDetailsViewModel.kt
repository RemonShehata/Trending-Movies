package com.example.trendingmovies.features.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.core.source.repos.ConfigurationRepo
import com.example.trendingmovies.core.models.MovieDetailsDto
import com.example.trendingmovies.core.source.repos.MovieDetailsRepo
import com.example.trendingmovies.utils.toMovieDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepo: MovieDetailsRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val movieDetailsMutableLiveData: MutableLiveData<MovieDetailsDto> = MutableLiveData()
    val movieDetailsLiveData: LiveData<MovieDetailsDto> = movieDetailsMutableLiveData

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = movieDetailsRepo.getMovieDetails(movieId)
            val config = configurationRepo.getConfiguration()
            val movie = config toMovieDetailsDto result
            movieDetailsMutableLiveData.postValue(movie)
        }
    }
}