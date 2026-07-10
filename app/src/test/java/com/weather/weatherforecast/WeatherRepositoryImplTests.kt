package com.weather.weatherforecast


import CurrentDto
import DailyDto
import HourlyDto
import WeatherResponse
import com.weather.weatherforecast.data.WeatherApi
import com.weather.weatherforecast.data.WeatherDao
import com.weather.weatherforecast.data.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var api: WeatherApi
    private lateinit var dao: WeatherDao
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {

        api = mockk()

        dao = mockk(relaxed = true)

        repository = WeatherRepositoryImpl(
            api,
            dao
        )
    }

    @Test
    fun refreshWeatherShouldSaveApiResponseIntoDatabase() = runTest {

        val response = WeatherResponse(
            current = CurrentDto(
                temperature = 30.0,
                weatherCode = 1
            ),
            hourly = HourlyDto(
                time = listOf("10:00"),
                temperature = listOf(30.0)
            ),
            daily = DailyDto(
                time = listOf("2026-07-10"),
                weatherCode = listOf(1),
                maxTemperature = listOf(33.0),
                minTemperature = listOf(25.0)
            )
        )

        every {
            dao.getWeather(any())
        } returns flowOf(null)

        coEvery {
            api.getWeather(any(), any())
        } returns response

        repository.refreshWeather(
            city = "Hyderabad",
            latitude = 17.38,
            longitude = 78.48
        )

        coVerify (exactly = 1) {
            dao.insertWeather(any())
        }
    }
}