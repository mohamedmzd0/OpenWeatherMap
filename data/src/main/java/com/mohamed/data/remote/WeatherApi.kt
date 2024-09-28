package com.mohamed.data.remote

import com.mohamed.data.BuildConfig
import com.mohamed.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String =BuildConfig.API_KEY
    ): WeatherResponse
}