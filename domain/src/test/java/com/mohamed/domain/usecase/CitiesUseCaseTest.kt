package com.mohamed.domain.usecase

import com.mohamed.core.utils.GlobalErrorHandler
import com.mohamed.domain.entity.CityEntity
import com.mohamed.domain.repository.GetCityRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitiesUseCaseTest {

    @Mock
    private lateinit var getCityRepository: GetCityRepository

    @Mock
    private lateinit var errorHandler: GlobalErrorHandler

    private lateinit var citiesUseCase: CitiesUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        citiesUseCase = CitiesUseCase(getCityRepository, errorHandler)
    }


    @Test
    fun `execute returns cities data from repository`() = runTest {
        val mockWeatherData = listOf(CityEntity(1, "", 10.0, 20.0))
        val repositoryFlow = flowOf(mockWeatherData)
        `when`(getCityRepository.getCities()).thenReturn(repositoryFlow)
        `when`(errorHandler.handleError(repositoryFlow)).thenReturn(
            flowOf(
                Result.success(
                    mockWeatherData
                )
            )
        )
        val result = citiesUseCase().single()
        Assert.assertEquals(1, result.getOrNull()?.size)
        Assert.assertTrue(result.isSuccess)
        Mockito.verify(getCityRepository).getCities()
    }

    @Test
    fun `execute should handle errors properly`() = runTest {
        `when`(getCityRepository.getCities()).thenThrow(RuntimeException("Error fetching cities"))
        try {
            citiesUseCase().firstOrNull()
            assert(false) // Should not reach here
        } catch (e: Exception) {
            assert(e.message == "Error fetching cities") // Verify the exception is thrown
        }
    }
}
