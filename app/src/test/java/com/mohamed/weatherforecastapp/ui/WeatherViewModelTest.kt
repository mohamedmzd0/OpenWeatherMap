package com.mohamed.weatherforecastapp.ui

import app.cash.turbine.test
import com.mohamed.core.utils.NetworkException
import com.mohamed.core.utils.NetworkState
import com.mohamed.domain.entity.CityEntity
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.domain.usecase.CitiesUseCase
import com.mohamed.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Mock
    private lateinit var citiesUseCase: CitiesUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = WeatherViewModel(getWeatherUseCase, citiesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `initialization should emit successful city state`() = runBlocking {
        val cities =
            listOf(CityEntity(1, "City 1", 10.0, 11.0), CityEntity(2, "City 2", 20.0, 21.0))
        `when`(citiesUseCase()).thenReturn(flow { emit(Result.success(cities)) })
        viewModel = WeatherViewModel(getWeatherUseCase, citiesUseCase)

        viewModel.cityState.test {
            assertEquals(NetworkState.Initial, awaitItem())
            assertEquals(NetworkState.Success(cities), awaitItem())
        }
    }


    @Test
    fun `getWeather should emit successful weather state`() = runBlocking {
        val weather = listOf(
            WeatherEntity("2024-09-28", "sunny_icon.png", "Sunny")
        )
        `when`(getWeatherUseCase(Pair("10.0", "11.0"))).thenReturn(flow {
            emit(
                Result.success(
                    Pair(true,weather)
                )
            )
        })

        viewModel.getWeather("10.0", "11.0")

        viewModel.weatherState.test {
            assertEquals(NetworkState.Initial, awaitItem())
            assertEquals(NetworkState.Success(weather), awaitItem())
        }
    }


    @Test
    fun `retry should fetch cities again if weather state is error`(): Unit = runBlockingTest {

        val cities =
            listOf(CityEntity(1, "City 1", 10.0, 11.0), CityEntity(2, "City 2", 20.0, 21.0))
        `when`(citiesUseCase()).thenReturn(flow { emit(Result.success(cities)) })
        `when`(getWeatherUseCase(Pair("10.0", "11.0"))).thenThrow(NetworkException("error"))

        viewModel = WeatherViewModel(getWeatherUseCase, citiesUseCase)

        viewModel.cityState.test {
            assertEquals(NetworkState.Initial, awaitItem())
            assertEquals(NetworkState.Success(cities), awaitItem())
        }
        viewModel.retry()
        verify(viewModel).getWeather("10.0", "11.0")
    }
}
