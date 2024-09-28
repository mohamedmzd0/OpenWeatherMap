package com.mohamed.core.base

import androidx.lifecycle.ViewModel
import com.mohamed.core.utils.HttpException
import com.mohamed.core.utils.NetworkException
import com.mohamed.core.utils.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

open class BaseViewModel : ViewModel() {
    protected fun <T> handleNetworkState(flow: Flow<Result<T>>): Flow<NetworkState<T>> {
        return flow
            .map { result ->
                when {
                    result.isSuccess -> NetworkState.Success(result.getOrNull()!!)
                    else -> {
                        when (val exception = result.exceptionOrNull()) {
                            is NetworkException -> NetworkState.Error("Network error")
                            is HttpException -> NetworkState.Error("HTTP error ${exception.code}: ${exception.message}")
                            else -> NetworkState.Error("An unexpected error occurred: ${exception?.message}")
                        }
                    }
                }
            }
            .catch { emit(NetworkState.Error("An unexpected error occurred: ${it.message}")) }
    }
}