package com.mohamed.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohamed.data.models.WeatherResponse

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherResponse(weatherResponse: WeatherResponse)

    @Query("SELECT * FROM weather_response WHERE lat = :lat AND lon = :lon")
    suspend fun getWeatherResponse(lat: Double, lon: Double): WeatherResponse?
}