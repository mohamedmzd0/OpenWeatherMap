package com.mohamed.data.repository

import com.mohamed.data.locale.WeatherDao
import com.mohamed.data.mappers.WeatherMapper
import com.mohamed.data.models.WeatherResponse
import com.mohamed.data.remote.WeatherApi
import com.mohamed.domain.entity.WeatherEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetWeatherRepositoryImplTest {

    @Mock
    private lateinit var weatherApi: WeatherApi

    @Mock
    private lateinit var weatherMapper: WeatherMapper

    @Mock
    private lateinit var weatherDao: WeatherDao

    private lateinit var getWeatherRepositoryImpl: GetWeatherRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWeatherRepositoryImpl = GetWeatherRepositoryImpl(weatherApi, weatherMapper, weatherDao)
    }

    @Test
    fun `invoke should return mapped weather from API when successful`() = runTest {

        val lat = "40.7128"
        val lon = "-74.0060"
        val weatherResponse = WeatherResponse(cod = "200", lat = 40.7128, lon = -74.0060)
        val mappedWeather = listOf(WeatherEntity("2023-09-28", "icon.png", "Sunny"))

        `when`(weatherApi.getWeather(lat, lon)).thenReturn(weatherResponse)
        `when`(weatherMapper.map(weatherResponse)).thenReturn(mappedWeather)

        // When
        val result = getWeatherRepositoryImpl.invoke(lat, lon).first()

        // Then
        assertEquals(Pair(true,mappedWeather), result)
        verify(weatherDao).insertWeatherResponse(weatherResponse)
    }

    @Test
    fun `invoke should return cached weather when API call fails`() = runTest {

        val lat = "40.7128"
        val lon = "-74.0060"
        val cachedWeatherResponse = WeatherResponse(cod = "200", lat = 40.7128, lon = -74.0060)
        val mappedCachedWeather = listOf(WeatherEntity("2023-09-28", "icon.png", "Cloudy"))

        `when`(weatherApi.getWeather(lat, lon)).thenThrow(RuntimeException("API error"))
        `when`(weatherDao.getWeatherResponse(40.7128, -74.0060)).thenReturn(cachedWeatherResponse)
        `when`(weatherMapper.map(cachedWeatherResponse)).thenReturn(mappedCachedWeather)

        // When
        val result = getWeatherRepositoryImpl.invoke(lat, lon).first()

        // Then
        assertEquals(Pair(false,mappedCachedWeather), result)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke should throw exception when API fails and no cached data available`() = runTest {

        val lat = "40.7128"
        val lon = "-74.0060"

        `when`(weatherApi.getWeather(lat, lon)).thenThrow(RuntimeException("API error"))
        `when`(weatherDao.getWeatherResponse(40.7128, -74.0060)).thenReturn(null)

        // When
        getWeatherRepositoryImpl.invoke(lat, lon).first()

        // Then: Exception is thrown
    }
}