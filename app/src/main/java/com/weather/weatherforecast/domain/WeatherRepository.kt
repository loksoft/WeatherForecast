package com.weather.weatherforecast.domain

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(
        city: String
    ): Flow<Weather>

    suspend fun refreshWeather(
        city: String,
        latitude: Double,
        longitude: Double
    )
}