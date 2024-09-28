package com.mohamed.core.utils

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlingConverterFactory : Converter.Factory() {
    private val defaultFactory: Converter.Factory = GsonConverterFactory.create()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val delegate = defaultFactory.responseBodyConverter(type, annotations, retrofit)
        return delegate?.let { ErrorHandlingConverter(it) }
    }

    private inner class ErrorHandlingConverter<T>(
        private val delegate: Converter<ResponseBody, T>
    ) : Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T? {
            return try {
                delegate.convert(value)
            } catch (e: Exception) {
                throw asRetrofitException(e)
            }
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            return when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    RetrofitException.httpError(
                        response!!,
                        throwable
                    )
                }

                is IOException, is SocketTimeoutException, is UnknownHostException ->
                    RetrofitException.networkError(throwable)

                else -> RetrofitException.unexpectedError(throwable)
            }
        }
    }

    companion object {
        fun create(): Converter.Factory = ErrorHandlingConverterFactory()
    }
}


class RetrofitException(
    message: String,
    val response: Response<*>? = null,
    val kind: Kind,
    exception: Throwable? = null
) : RuntimeException(message, exception) {

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    companion object {
        fun httpError(response: Response<*>, throwable: Throwable): RetrofitException {
            return RetrofitException(
                "HTTP ${response.code()} ${response.message()}",
                response,
                Kind.HTTP,
                throwable
            )
        }

        fun networkError(throwable: Throwable): RetrofitException {
            return RetrofitException(
                "Network error: ${throwable.message}",
                null,
                Kind.NETWORK,
                throwable
            )
        }

        fun unexpectedError(throwable: Throwable): RetrofitException {
            return RetrofitException(
                "Unexpected error: ${throwable.message}",
                null,
                Kind.UNEXPECTED,
                throwable
            )
        }
    }
}

