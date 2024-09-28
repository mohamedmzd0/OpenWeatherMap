package com.mohamed.data.locale.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mohamed.data.models.Coord

class CoordConverter {
    @TypeConverter
    fun fromCoord(coord: Coord?): String {
        return Gson().toJson(coord)
    }

    @TypeConverter
    fun toCoord(value: String): Coord? {
        return Gson().fromJson(value, Coord::class.java)
    }
}