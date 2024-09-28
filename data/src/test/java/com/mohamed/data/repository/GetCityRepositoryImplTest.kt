package com.mohamed.data.repository

import com.mohamed.data.locale.CityDao
import com.mohamed.data.mappers.CitiesMapper
import com.mohamed.data.models.CitiesResponse
import com.mohamed.data.models.CityData
import com.mohamed.data.remote.CitiesApi
import com.mohamed.domain.entity.CityEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCityRepositoryImplTest {

    @Mock
    private lateinit var citiesApi: CitiesApi

    @Mock
    private lateinit var citiesMapper: CitiesMapper

    @Mock
    private lateinit var cityDao: CityDao

    private lateinit var getCityRepositoryImpl: GetCityRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getCityRepositoryImpl = GetCityRepositoryImpl(citiesApi, citiesMapper, cityDao)
    }

    @Test
    fun `getCities should return mapped cities from API when successful`() = runTest {

        val citiesResponse =
            CitiesResponse(listOf(CityData(1, "New York", "نيويورك", 40.7128, -74.0060)))
        val mappedCities = listOf(CityEntity(1, "New York", 40.7128, -74.0060))

        `when`(citiesApi.getCities()).thenReturn(citiesResponse)
        `when`(citiesMapper.map(citiesResponse.cities!!)).thenReturn(mappedCities)

        // When
        val result = getCityRepositoryImpl.getCities().first()

        // Then
        assertEquals(mappedCities, result)
        verify(cityDao).insertCities(citiesResponse.cities!!)
    }

    @Test
    fun `getCities should return cached cities when API call fails`() = runTest {

        val cachedCities = listOf(CityData(1, "London", "لندن", 51.5074, -0.1278))
        val mappedCachedCities = listOf(CityEntity(1, "London", 51.5074, -0.1278))

        `when`(citiesApi.getCities()).thenThrow(RuntimeException("API error"))
        `when`(cityDao.getAllCities()).thenReturn(cachedCities)
        `when`(citiesMapper.map(cachedCities)).thenReturn(mappedCachedCities)

        // When
        val result = getCityRepositoryImpl.getCities().first()

        // Then
        assertEquals(mappedCachedCities, result)
    }

    @Test
    fun `getCities should throw exception when API fails and no cached data available`() = runTest {

        `when`(citiesApi.getCities()).thenThrow(RuntimeException("API error"))
        `when`(cityDao.getAllCities()).thenReturn(null)
        try {
            getCityRepositoryImpl.getCities().firstOrNull()
            assert(false)
        } catch (e: Exception) {
            assert(e.message == "API error")
        }
    }
}