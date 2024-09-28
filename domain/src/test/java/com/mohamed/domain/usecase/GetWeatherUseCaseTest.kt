package com.mohamed.domain.usecase

import com.mohamed.core.utils.GlobalErrorHandler
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.domain.repository.GetWeatherRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)

class GetWeatherUseCaseTest {

    @Mock
    private lateinit var getWeatherRepository: GetWeatherRepository

    @Mock
    private lateinit var errorHandler: GlobalErrorHandler

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWeatherUseCase = GetWeatherUseCase(getWeatherRepository, errorHandler)
    }

    @Test
    fun `execute returns weather data from repository`() = runTest {
        val mockWeatherData = listOf(WeatherEntity("", "", ""))
        val repositoryFlow = flowOf(Pair(true,mockWeatherData))
        `when`(getWeatherRepository.invoke("", "")).thenReturn(repositoryFlow)
        `when`(errorHandler.handleError(repositoryFlow)).thenReturn(
            flowOf(
                Result.success(
                    Pair(true, mockWeatherData)
                )
            )
        )
        val result = getWeatherUseCase(Pair("", "")).single()
        assertEquals(1, result.getOrNull()?.second?.size)
        assertTrue(result.isSuccess)
        verify(getWeatherRepository).invoke("", "")
    }

    @Test
    fun `execute handles error using error handler`() = runTest {
        val exception = RuntimeException("Error fetching weather data")
        val errorFlow = flowOf<Pair<Boolean,List<WeatherEntity>>>()
        `when`(getWeatherRepository.invoke("", "")).thenReturn(errorFlow)
        `when`(errorHandler.handleError(errorFlow)).thenReturn(flowOf(Result.failure(exception)))
        val result = getWeatherUseCase(Pair("", "")).toList()
        assertEquals(1, result.size)
    }
}