package com.example.trendingmovies

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val errorType: ErrorType) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}

sealed class ErrorType {
    object NoInternet : ErrorType()
    object ReachedEndOfList : ErrorType()
}
