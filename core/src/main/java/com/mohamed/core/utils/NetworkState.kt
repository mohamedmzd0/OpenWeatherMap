package com.mohamed.core.utils


sealed class NetworkState<out T> {
    data object Initial : NetworkState<Nothing>()
    data object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val message: String) : NetworkState<Nothing>()
}