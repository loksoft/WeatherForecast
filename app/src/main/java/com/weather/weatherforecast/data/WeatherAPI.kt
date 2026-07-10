package com.weather.weatherforecast.data

import WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherApi {

    @GET("forecast")
    suspend fun getWeather(

        @Query("latitude")
        latitude: Double,

        @Query("longitude")
        longitude: Double,

        @Query("current")
        current: String = "temperature_2m,weather_code",

        @Query("hourly")
        hourly: String = "temperature_2m",

        @Query("daily")
        daily: String = "weather_code,temperature_2m_max,temperature_2m_min",

        @Query("timezone")
        timezone: String = "Asia/Singapore"

    ): WeatherResponse
}