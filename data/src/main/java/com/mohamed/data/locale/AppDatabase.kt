package com.mohamed.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamed.data.locale.converters.CityConverter
import com.mohamed.data.locale.converters.CoordConverter
import com.mohamed.data.locale.converters.WeatherConverter
import com.mohamed.data.locale.converters.WeatherItemConverter
import com.mohamed.data.models.City
import com.mohamed.data.models.CityData
import com.mohamed.data.models.Coord
import com.mohamed.data.models.Weather
import com.mohamed.data.models.WeatherItem
import com.mohamed.data.models.WeatherResponse

@Database(
    entities = [
        CityData::class,
        WeatherResponse::class,
        WeatherItem::class,
        Weather::class,
        City::class,
        Coord::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    WeatherItemConverter::class,
    CoordConverter::class,
    CityConverter::class,
    WeatherConverter::class,
    WeatherItemConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

}
