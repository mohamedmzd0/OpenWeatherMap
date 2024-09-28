package com.mohamed.data.mappers

import com.mohamed.data.models.Weather
import com.mohamed.data.models.WeatherItem
import com.mohamed.data.models.WeatherResponse
import com.mohamed.domain.entity.WeatherEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherMapperTest {

    private lateinit var weatherMapper: WeatherMapper

    @Before
    fun setup() {
        weatherMapper = WeatherMapper()
    }

    @Test
    fun `map should return correct WeatherEntity list`() {

        val weatherResponse = WeatherResponse(
            cod = "200",
            lat = 40.7128,
            lon = -74.0060,
            weatherItem = listOf(
                WeatherItem(
                    dtTxt = "2023-09-28 12:00:00",
                    weather = listOf(
                        Weather(
                            icon = "01d",
                            description = "clear sky"
                        )
                    )
                ),
                WeatherItem(
                    dtTxt = "2023-09-28 15:00:00",
                    weather = listOf(
                        Weather(
                            icon = "02d",
                            description = "few clouds"
                        )
                    )
                )
            )
        )

        // When
        val result = weatherMapper.map(weatherResponse)

        // Then
        assertEquals(2, result.size)
        assertEquals(
            WeatherEntity(
                formattedDate = formatDate("2023-09-28 12:00:00"),
                icon = "http://openweathermap.org/img/w/01d.png",
                description = "clear sky"
            ), result[0]
        )
        assertEquals(
            WeatherEntity(
                formattedDate = formatDate("2023-09-28 15:00:00"),
                icon = "http://openweathermap.org/img/w/02d.png",
                description = "few clouds"
            ), result[1]
        )
    }

    @Test
    fun `map should handle empty weather list`() {

        val weatherResponse = WeatherResponse(
            cod = "200",
            lat = 40.7128,
            lon = -74.0060,
            weatherItem = emptyList()
        )

        // When
        val result = weatherMapper.map(weatherResponse)
        // Then
        assertTrue(result.isEmpty())
    }


    private fun formatDate(
        dateString: String,
        fromPattern: String = "yyyy-MM-dd HH:mm:ss",
        toPattern: String = "EEEE, MMMM d, yyyy"
    ): String {
        return try {
            val inputFormat = SimpleDateFormat(fromPattern, Locale.US)
            val outputFormat = SimpleDateFormat(toPattern, Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}