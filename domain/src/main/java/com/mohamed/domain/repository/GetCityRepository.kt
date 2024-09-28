package com.mohamed.domain.repository

import com.mohamed.domain.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface GetCityRepository {
    suspend fun getCities(): Flow<List<CityEntity>>
}