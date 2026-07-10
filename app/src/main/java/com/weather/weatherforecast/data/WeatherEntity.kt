package com.weather.weatherforecast.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** For offline setup using the Room database**/
@Entity(tableName = "weather")
data class WeatherEntity(

    @PrimaryKey
    val city: String,

    val currentTemperature: Double,

    val currentWeatherCode: Int,

    val hourlyTimes: List<String>,

    val hourlyTemperatures: List<Double>,

    val dailyDates: List<String>,

    val dailyWeatherCodes: List<Int>,

    val maxTemperatures: List<Double>,

    val minTemperatures: List<Double>,

    val lastUpdated: Long
)