package com.example.trendingmovies.details

import androidx.lifecycle.ViewModel
import com.example.trendingmovies.ConfigurationRepo
import com.example.trendingmovies.MovieDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepo: MovieDetailsRepo,
    private val configurationRepo: ConfigurationRepo,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

}