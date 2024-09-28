package com.mohamed.core.base

import com.mohamed.core.utils.GlobalErrorHandler
import kotlinx.coroutines.flow.Flow


abstract class UseCase<in P, R>(private val errorHandler: GlobalErrorHandler) {
    suspend operator fun invoke(parameters: P): Flow<Result<R>> {
        return errorHandler.handleError(execute(parameters))
    }

    protected abstract suspend fun execute(parameters: P): Flow<R>
}

// For use cases that don't need parameters
abstract class NoParamUseCase<R>(private val errorHandler: GlobalErrorHandler) {
    suspend operator fun invoke(): Flow<Result<R>> {
        return errorHandler.handleError(execute())
    }

    protected abstract suspend fun execute(): Flow<R>
}