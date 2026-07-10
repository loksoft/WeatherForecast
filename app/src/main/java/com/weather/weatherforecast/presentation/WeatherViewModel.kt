package com.weather.weatherforecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.weatherforecast.domain.GetWeatherUseCase
import com.weather.weatherforecast.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun loadWeather(
        city: String,
        latitude: Double,
        longitude: Double
    ) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                repository.refreshWeather(
                    city = city,
                    latitude = latitude,
                    longitude = longitude
                )

                getWeatherUseCase(city)
                    .catch { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                    .collectLatest { weather ->

                        _uiState.value = WeatherUiState(
                            isLoading = false,
                            weather = weather
                        )
                    }

            } catch (e: Exception) {

                _uiState.value = WeatherUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}