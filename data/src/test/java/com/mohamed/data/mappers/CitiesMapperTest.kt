package com.mohamed.data.mappers

import com.mohamed.data.models.CityData
import com.mohamed.domain.entity.CityEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale

class CitiesMapperTest {

    private lateinit var citiesMapper: CitiesMapper

    @Before
    fun setup() {
        citiesMapper = CitiesMapper()
    }

    @Test
    fun `map should return correct CityEntity list for English locale`() {

        Locale.setDefault(Locale("en"))
        val cities = listOf(
            CityData(1, "نيويورك", "New York", 40.7128, -74.0060),
            CityData(2, "لندن", "London", 51.5074, -0.1278)
        )

        // When
        val result = citiesMapper.map(cities)

        // Then
        assertEquals(2, result.size)
        assertEquals(CityEntity(1, "New York", 40.7128, -74.0060), result[0])
        assertEquals(CityEntity(2, "London", 51.5074, -0.1278), result[1])
    }

    @Test
    fun `map should return correct CityEntity list for Arabic locale`() {

        Locale.setDefault(Locale("ar"))
        val cities = listOf(
            CityData(1, "نيويورك", "New York", 40.7128, -74.0060),
            CityData(2, "لندن", "London", 51.5074, -0.1278)
        )

        // When
        val result = citiesMapper.map(cities)

        // Then
        assertEquals(2, result.size)
        assertEquals(CityEntity(1, "نيويورك", 40.7128, -74.0060), result[0])
        assertEquals(CityEntity(2, "لندن", 51.5074, -0.1278), result[1])
    }

    @Test
    fun `map should return empty list for empty input`() {

        val cities = emptyList<CityData>()

        // When
        val result = citiesMapper.map(cities)

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `map should return English name for non-Arabic locale`() {

        Locale.setDefault(Locale("fr"))
        val cities = listOf(
            CityData(1, "باريس", "Paris", 48.8566, 2.3522)
        )

        // When
        val result = citiesMapper.map(cities)

        // Then
        assertEquals(1, result.size)
        assertEquals(CityEntity(1, "Paris", 48.8566, 2.3522), result[0])
    }
}