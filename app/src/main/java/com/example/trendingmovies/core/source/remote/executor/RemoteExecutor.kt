@file:Suppress("ThrowsCount", "WildcardImport", "TooGenericExceptionCaught", "MagicNumber")

package com.example.trendingmovies.core.source.remote.executor

import com.example.trendingmovies.core.source.remote.*
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteExecutor {
    @Throws(
        ResponseParsingException::class,
        UnAuthorizedException::class,
        ServerErrorException::class,
        ResourceNotFoundException::class,
        UnknownErrorException::class,
        NoNetworkConnectionException::class
    )
    protected suspend fun <R> executeApiCall(apiCall: suspend () -> R): R? {
        return try {
            apiCall.invoke()
        } catch (cause: Throwable) {
            mapApiCallException(cause)
            null
        }
    }

    private fun mapApiCallException(cause: Throwable) {
        when (cause) {
            is HttpException -> mapErrorBodyResponse(cause.response())
            is UnknownHostException -> throw NoNetworkConnectionException()
            is SocketTimeoutException -> throw NoNetworkConnectionException()
            is JsonDataException -> {
                throw ResponseParsingException()
            }
            else -> throw cause
        }
    }

    private fun mapErrorBodyResponse(response: Response<*>?) {
        when {
            response?.code() == 401 -> {
                throw UnAuthorizedException()
            }
            response?.code() == 500 || response?.code() == 503 -> {
                throw ServerErrorException(response.code())
            }
            response?.code() == 404 -> {
                throw ResourceNotFoundException()
            }
            else -> {
                throw UnknownErrorException()
            }
        }
    }
}
