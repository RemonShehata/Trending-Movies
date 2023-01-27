package com.example.trendingmovies.features.movies_list

import android.util.Log
import androidx.lifecycle.*
import com.example.trendingmovies.base.TAG
import com.example.trendingmovies.core.models.ErrorType
import com.example.trendingmovies.core.models.State
import com.example.trendingmovies.core.models.TrendingMoviesDto
import com.example.trendingmovies.core.source.remote.*
import com.example.trendingmovies.core.source.repos.ConfigurationRepo
import com.example.trendingmovies.core.source.repos.TrendingMoviesRepo
import com.example.trendingmovies.utils.NetworkState
import com.example.trendingmovies.utils.NetworkStateMonitor
import com.example.trendingmovies.utils.toTrendingMovieDtoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val trendingMoviesRepo: TrendingMoviesRepo,
    private val configurationRepo: ConfigurationRepo,
    private val networkStateMonitor: NetworkStateMonitor,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val moviesMutableLiveData: MutableLiveData<State<List<TrendingMoviesDto>>> =
        MutableLiveData()

    val moviesLiveData: LiveData<State<List<TrendingMoviesDto>>> = moviesMutableLiveData

    private val isOnlineMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val isOnlineLiveData: LiveData<Boolean> = isOnlineMutableLiveData


    val mediatorLiveData = MediatorLiveData<Pair<State<List<TrendingMoviesDto>>?, Boolean?>>()

    var atBottomOfScreen: Boolean = false

    fun getNextPageData() {
        moviesMutableLiveData.value = State.Loading
        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "getNextPageData in viewModel ")
            networkCallWithExceptionHandling {
                trendingMoviesRepo.getMoviesForPage()
            }
        }
    }

    init {
        Log.d(TAG, "viewModel: init")
        moviesMutableLiveData.value = State.Loading

        // after movies have been inserted in DB
        // listen for db changes
        // then convert the entity into dto also getting full url in thr process
        viewModelScope.launch(ioDispatcher) {
            trendingMoviesRepo.getAllMoviesFlow().collect { moviesList ->
                Log.d(TAG, "viewModel: collect - size = ${moviesList.size}")
                networkCallWithExceptionHandling {
                    val configurationResult = configurationRepo.getConfiguration()
                    val movies =
                        configurationResult?.toTrendingMovieDtoList(moviesList)
                    movies?.let {  moviesMutableLiveData.postValue(State.Success(it)) }
                }
            }
        }

//        /Users/remon.shehata/Library/Android/sdk/tools/emulator
//        emulator -avd emulator-5554 -dns-server 8.8.8.8 -netspeed gsm -netdelay 2000


        // if we don't have movies in db
        // get page 1 from api, then insert movies into db
        viewModelScope.launch(ioDispatcher) {
            Log.d(TAG, "viewModel init: getting movies from api")
            getAllMovies()
        }

        // listen for network changes,
        // when we are in a connected state, we get list for the next page
        // TODO: get movies for the next page only when we are at the bottom of the recycler view
        viewModelScope.launch(ioDispatcher) {
            networkStateMonitor.networkStateFlow.collect { state ->
                Log.d(TAG, "networkState: $state")
                Log.d(TAG, "viewModel networkstate: getting movies from api")
                when (state) {
                    NetworkState.Connected -> {
//                        if (atBottomOfScreen){
                        Log.d(TAG, "network state on and at bottom of screen: ")
//                        trendingMoviesRepo.getMoviesForPage()
                        isOnlineMutableLiveData.postValue(true)
//                        }

                    }
                    NetworkState.Disconnected -> isOnlineMutableLiveData.postValue(false)
                }
            }
        }

        mediatorLiveData.addSource(isOnlineLiveData) {
            mediatorLiveData.value = Pair(moviesLiveData.value, it)
        }

        mediatorLiveData.addSource(moviesLiveData) {
            mediatorLiveData.value = Pair(it, isOnlineLiveData.value)
        }
    }

    private suspend fun getAllMovies() {
        networkCallWithExceptionHandling { trendingMoviesRepo.getAllMoviesSync() }
    }

    private suspend fun networkCallWithExceptionHandling(call: suspend () -> Unit) {
        try {
            call.invoke()
        } catch (noNetworkException: NoNetworkConnectionException) {
            moviesMutableLiveData.postValue(State.Error(ErrorType.NoInternet))
        } catch (responseParsingException: ResponseParsingException) {
            moviesMutableLiveData.postValue(
                State.Error(
                    ErrorType.RemoteResponseParsingError(
                        responseParsingException.message
                    )
                )
            )
        } catch (unAuthorizedException: UnAuthorizedException) {
            moviesMutableLiveData.postValue(State.Error(ErrorType.UnAuthorized))
        } catch (serverErrorException: ServerErrorException) {
            moviesMutableLiveData.postValue(
                State.Error(
                    ErrorType.ServerError(
                        serverErrorException.code,
                        serverErrorException.message
                    )
                )
            )
        } catch (resourceNotFoundException: ResourceNotFoundException) {
            moviesMutableLiveData.postValue(State.Error(ErrorType.ResourceNotFound))
        } catch (unknownErrorException: UnknownErrorException) {
            moviesMutableLiveData.postValue(
                State.Error(
                    ErrorType.UnknownError(unknownErrorException.message)
                )
            )
        } catch (exception: Exception) {
            moviesMutableLiveData.postValue(State.Error(ErrorType.UnknownError(exception.message)))
        }
    }
}

