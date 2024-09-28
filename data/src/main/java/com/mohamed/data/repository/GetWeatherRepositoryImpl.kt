package com.mohamed.data.repository

import com.mohamed.data.locale.WeatherDao
import com.mohamed.data.mappers.WeatherMapper
import com.mohamed.data.remote.WeatherApi
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.domain.repository.GetWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val mapper: WeatherMapper,
    private val weatherDao: WeatherDao
) : GetWeatherRepository {

    override suspend fun invoke(
        lat: String,
        lon: String
    ): Flow<Pair<Boolean, List<WeatherEntity>>> {
        return flow {
            val latitude = lat.toDouble()
            val longitude = lon.toDouble()
            try {
                val weatherResponse = api.getWeather(lat, lon)
                weatherDao.insertWeatherResponse(
                    weatherResponse.copy(lat = latitude, lon = longitude)
                )
                emit(Pair(true, mapper.map(weatherResponse)))
            } catch (e: Exception) {
                val cachedWeatherResponse = weatherDao.getWeatherResponse(latitude, longitude)
                if (cachedWeatherResponse != null) {
                    emit(Pair(false, mapper.map(cachedWeatherResponse)))
                } else {
                    throw e
                }
            }
        }
    }
}
