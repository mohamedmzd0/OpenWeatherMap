package com.mohamed.data.locale.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamed.data.models.Weather

class WeatherConverter {
    @TypeConverter
    fun fromWeatherList(value: List<Weather>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(value, listType)
    }
}