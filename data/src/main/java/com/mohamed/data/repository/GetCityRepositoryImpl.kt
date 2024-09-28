package com.mohamed.data.repository

import com.mohamed.data.locale.CityDao
import com.mohamed.data.mappers.CitiesMapper
import com.mohamed.data.remote.CitiesApi
import com.mohamed.domain.entity.CityEntity
import com.mohamed.domain.repository.GetCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCityRepositoryImpl @Inject constructor(
    private val citiesApi: CitiesApi,
    private val citiesMapper: CitiesMapper,
    private val cityDao: CityDao
) : GetCityRepository {

    override suspend fun getCities(): Flow<List<CityEntity>> = flow {
        try {
            val citiesResponse = citiesApi.getCities()
            val cities = citiesResponse.cities ?: emptyList()
            cityDao.insertCities(cities)
            emit(citiesMapper.map(cities))
        } catch (ex: Exception) {
            val cachedCities = cityDao.getAllCities()
            if (!cachedCities.isNullOrEmpty()) {
                emit(citiesMapper.map(cachedCities))
            } else {
                throw ex
            }
        }
    }
}
