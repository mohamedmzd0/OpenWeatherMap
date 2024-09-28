package com.mohamed.data.mappers

import com.mohamed.data.models.WeatherResponse
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.utils.date.formatDate
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    fun map(data: WeatherResponse): List<WeatherEntity> {
        return data.weatherItem.map { weatherItem ->
            val weatherInfo = weatherItem.weather.firstOrNull()
            WeatherEntity(
                formattedDate = formatDate(weatherItem.dtTxt ?: ""),
                icon = "http://openweathermap.org/img/w/${weatherInfo?.icon}.png",
                description = weatherInfo?.description.orEmpty()
            )
        }
    }
}