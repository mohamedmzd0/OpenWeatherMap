package com.mohamed.data.mappers

import com.mohamed.data.models.CityData
import com.mohamed.domain.entity.CityEntity
import java.util.Locale
import javax.inject.Inject

class CitiesMapper @Inject constructor() {

    fun map(cities: List<CityData>): List<CityEntity> {
        return cities.map { cityData ->
            CityEntity(
                id = cityData.id,
                name = getCityName(cityData),
                lat = cityData.lat,
                lon = cityData.lon
            )
        }
    }

    private fun getCityName(cityData: CityData): String {
        return when (Locale.getDefault().language) {
            "ar" -> cityData.cityNameAr
            else -> cityData.cityNameEn
        }
    }
}
