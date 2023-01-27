package com.example.trendingmovies.core.models

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val errorType: ErrorType) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}

sealed class ErrorType {
    object NoInternet : ErrorType()

    // TODO: This is not generic. it is not needed for details
    object NoInternetForNextPage : ErrorType()

    // TODO: This is not generic. it is not needed for details
    object ReachedEndOfList : ErrorType()

    data class RemoteResponseParsingError(val message: String?) : ErrorType()

    object UnAuthorized : ErrorType()

    data class ServerError(val code: Int, val message: String?) : ErrorType()

    object ResourceNotFound : ErrorType()
    data class UnknownError(val message: String?) : ErrorType()
}
