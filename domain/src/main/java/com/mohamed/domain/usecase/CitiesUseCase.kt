package com.mohamed.domain.usecase

import com.mohamed.core.base.NoParamUseCase
import com.mohamed.core.utils.GlobalErrorHandler
import com.mohamed.domain.entity.CityEntity
import com.mohamed.domain.repository.GetCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CitiesUseCase @Inject constructor(
    private val getCityRepository: GetCityRepository,
    errorHandler: GlobalErrorHandler
) : NoParamUseCase<List<CityEntity>>(errorHandler = errorHandler) {

    override suspend fun execute(): Flow<List<CityEntity>> {
        return getCityRepository.getCities()
    }

}