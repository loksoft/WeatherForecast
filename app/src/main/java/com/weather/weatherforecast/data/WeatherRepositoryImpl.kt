package com.weather.weatherforecast.data

import com.weather.weatherforecast.domain.CurrentWeather
import com.weather.weatherforecast.domain.DailyWeather
import com.weather.weatherforecast.domain.HourlyWeather
import com.weather.weatherforecast.domain.Weather
import com.weather.weatherforecast.domain.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    companion object {
        private const val CACHE_TTL = 30 * 60 * 1000L // 30 minutes
    }

    override fun getWeather(city: String): Flow<Weather> {
        return dao.getWeather(city).map { entity ->

            if (entity == null) {
                Weather(
                    current = CurrentWeather(
                        temperature = 0.0,
                        weatherCode = 0
                    ),
                    hourly = emptyList(),
                    daily = emptyList()
                )
            } else {
                mapToDomain(entity)
            }
        }
    }

    override suspend fun refreshWeather(
        city: String,
        latitude: Double,
        longitude: Double
    ) {

        val cachedWeather = dao.getWeather(city).firstOrNull()

        if (cachedWeather != null &&
            !shouldRefresh(cachedWeather.lastUpdated)
        ) {
            return
        }

        val response = api.getWeather(
            latitude = latitude,
            longitude = longitude
        )

        dao.insertWeather(
            WeatherEntity(
                city = city,
                currentTemperature = response.current.temperature,
                currentWeatherCode = response.current.weatherCode,
                hourlyTimes = response.hourly.time,
                hourlyTemperatures = response.hourly.temperature,
                dailyDates = response.daily.time,
                dailyWeatherCodes = response.daily.weatherCode,
                maxTemperatures = response.daily.maxTemperature,
                minTemperatures = response.daily.minTemperature,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    private fun shouldRefresh(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > CACHE_TTL
    }

    private fun mapToDomain(entity: WeatherEntity): Weather {

        val hourlyList = entity.hourlyTimes.mapIndexed { index, time ->
            HourlyWeather(
                time = time,
                temperature = entity.hourlyTemperatures[index]
            )
        }

        val dailyList = entity.dailyDates.mapIndexed { index, date ->
            DailyWeather(
                date = date,
                weatherCode = entity.dailyWeatherCodes[index],
                maxTemperature = entity.maxTemperatures[index],
                minTemperature = entity.minTemperatures[index]
            )
        }

        return Weather(
            current = CurrentWeather(
                temperature = entity.currentTemperature,
                weatherCode = entity.currentWeatherCode
            ),
            hourly = hourlyList,
            daily = dailyList
        )
    }
}