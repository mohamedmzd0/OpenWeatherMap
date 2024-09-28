package com.mohamed.domain.repository

import com.mohamed.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

fun interface GetWeatherRepository {
    suspend fun invoke(lat: String, lon: String): Flow<Pair<Boolean,List<WeatherEntity>>>
}