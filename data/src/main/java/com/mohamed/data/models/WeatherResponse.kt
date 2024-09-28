package com.mohamed.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mohamed.data.locale.converters.CityConverter
import com.mohamed.data.locale.converters.CoordConverter
import com.mohamed.data.locale.converters.WeatherConverter
import com.mohamed.data.locale.converters.WeatherItemConverter

@Entity(tableName = "city")
data class City(
    @PrimaryKey val id: Int? = null,
    val name: String? = null,
    @TypeConverters(CoordConverter::class) val coord: Coord? = null,
    val country: String? = null
)

@Entity(tableName = "coord", primaryKeys = ["lat", "lon"])
data class Coord(
    val lat: Double,
    val lon: Double
)

@Entity(tableName = "weather_item")
data class WeatherItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dt: Int? = null,
    @SerializedName("weather")
    @TypeConverters(WeatherConverter::class) val weather: List<Weather> = listOf(),
    @SerializedName("dt_txt")
    val dtTxt: String? = null
)

@Entity
data class Weather(
    @PrimaryKey val id: Int? = null, val description: String? = null, val icon: String? = null
)

@Entity(tableName = "weather_response", primaryKeys = ["lat", "lon"])
data class WeatherResponse(
    @SerializedName("cod")
    val cod: String? = null,
    val lat: Double,
    val lon: Double,
    @SerializedName("list")
    @TypeConverters(WeatherItemConverter::class)
    val weatherItem: List<WeatherItem> = listOf(),
    @TypeConverters(CityConverter::class) val city: City? = null
)
