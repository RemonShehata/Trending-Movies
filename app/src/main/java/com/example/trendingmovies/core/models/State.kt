package com.example.trendingmovies.core.models

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val errorType: ErrorType) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}

sealed class ErrorType {
    object NoInternet : ErrorType()
    object NoInternetForNextPage : ErrorType()
    object ReachedEndOfList : ErrorType()
    object UnknownError: ErrorType()
}
