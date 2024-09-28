package com.mohamed.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CitiesResponse(
    val cities: List<CityData>? = null
)

@Entity(tableName = "cities")
data class CityData(
    @PrimaryKey val id: Int,
    val cityNameAr: String,
    val cityNameEn: String,
    val lat: Double,
    val lon: Double
)