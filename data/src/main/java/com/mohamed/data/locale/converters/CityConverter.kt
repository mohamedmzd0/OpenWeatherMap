package com.mohamed.data.locale.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mohamed.data.models.City

class CityConverter {
    @TypeConverter
    fun fromCity(city: City?): String {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(value: String): City? {
        return Gson().fromJson(value, City::class.java)
    }
}