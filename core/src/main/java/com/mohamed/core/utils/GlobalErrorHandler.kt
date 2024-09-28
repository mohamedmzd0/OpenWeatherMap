package com.mohamed.core.utils


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class GlobalErrorHandler {
    fun <T> handleError(flow: Flow<T>): Flow<Result<T>> {
        return flow.map { Result.success(it) }.catch { e ->
            emit(Result.failure(mapException(e)))
        }
    }

    private fun mapException(throwable: Throwable): Exception {
        return when (throwable) {
            is RetrofitException -> {
                when (throwable.kind) {
                    RetrofitException.Kind.NETWORK -> NetworkException(
                        throwable.message ?: "Network error occurred"
                    )

                    RetrofitException.Kind.HTTP -> HttpException(
                        throwable.response?.code() ?: 0, throwable.message ?: "HTTP error occurred"
                    )

                    RetrofitException.Kind.UNEXPECTED -> UnexpectedException(
                        throwable.message ?: "An unexpected error occurred"
                    )
                }
            }

            is IOException -> NetworkException("Network error: ${throwable.message}")
            else -> UnexpectedException("An unexpected error occurred: ${throwable.message}")
        }
    }
}

class NetworkException(message: String) : Exception(message)
class HttpException(val code: Int, message: String) : Exception(message)
class UnexpectedException(message: String) : Exception(message)