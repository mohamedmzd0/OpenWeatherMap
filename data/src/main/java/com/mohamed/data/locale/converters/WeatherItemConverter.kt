package com.mohamed.data.locale.converters


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamed.data.models.WeatherItem

class WeatherItemConverter {
    @TypeConverter
    fun fromWeatherItemList(value: List<WeatherItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWeatherItemList(value: String): List<WeatherItem> {
        val listType = object : TypeToken<List<WeatherItem>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

