package com.example.trendingmovies.core.source.remote

class ResponseParsingException(): Exception()
class UnAuthorizedException : Exception()
class ServerErrorException(val code: Int) : Exception()
class ResourceNotFoundException : Exception()
class UnknownErrorException: Exception()
class NoNetworkConnectionException : Exception() {
    override val message: String
        get() = "No network available, Please check your WiFi or Data connection"
}