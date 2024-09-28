package com.mohamed.weatherforecastapp.ui

import androidx.lifecycle.viewModelScope
import com.mohamed.core.base.BaseViewModel
import com.mohamed.core.utils.NetworkState
import com.mohamed.domain.entity.CityEntity
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.domain.usecase.CitiesUseCase
import com.mohamed.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase,
    private val citiesUseCase: CitiesUseCase
) : BaseViewModel() {

    private var latLng = Pair("", "")

    private val _weatherState =
        MutableStateFlow<NetworkState<Pair<Boolean,List<WeatherEntity>>>>(NetworkState.Initial)
    val weatherState = _weatherState.asStateFlow()

    private val _cityState = MutableStateFlow<NetworkState<List<CityEntity>>>(NetworkState.Initial)
    val cityState = _cityState.asStateFlow()

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            handleNetworkState(citiesUseCase()).collect { state ->
                _cityState.value = state
            }
        }
    }

    fun getWeather(lat: String, lon: String) {
        latLng = Pair(lat, lon)
        fetchWeatherData(lat, lon)
    }

    private fun fetchWeatherData(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            handleNetworkState(useCase.invoke(Pair(lat, lon))).collect { state ->
                _weatherState.value = state
            }
        }
    }

    fun retry() {
        when {
            _cityState.value is NetworkState.Error -> getCities()
            _weatherState.value is NetworkState.Error -> fetchWeatherData(
                latLng.first,
                latLng.second
            )
        }
    }

}
