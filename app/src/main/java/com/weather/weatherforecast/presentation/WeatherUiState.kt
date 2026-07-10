package com.weather.weatherforecast.presentation

import com.weather.weatherforecast.domain.Weather

data class WeatherUiState(

    val isLoading: Boolean = false,

    val weather: Weather? = null,

    val error: String? = null
)