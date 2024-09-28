package com.mohamed.domain.usecase

import com.mohamed.core.base.UseCase
import com.mohamed.core.utils.GlobalErrorHandler
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.domain.repository.GetWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(
    private val getWeatherRepository: GetWeatherRepository,
    errorHandler: GlobalErrorHandler
) : UseCase<Pair<String, String>, Pair<Boolean, List<WeatherEntity>>>(errorHandler) {

    override suspend fun execute(parameters: Pair<String, String>): Flow<Pair<Boolean, List<WeatherEntity>>> {
        return getWeatherRepository.invoke(parameters.first, parameters.second)
    }
}