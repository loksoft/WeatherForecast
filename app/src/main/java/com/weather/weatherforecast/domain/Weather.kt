package com.weather.weatherforecast.domain

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    val temperature: Double,
    val weatherCode: Int
)

data class HourlyWeather(
    val time: String,
    val temperature: Double
)

data class DailyWeather(
    val date: String,
    val weatherCode: Int,
    val maxTemperature: Double,
    val minTemperature: Double
)