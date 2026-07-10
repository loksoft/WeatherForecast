package com.weather.weatherforecast


import com.weather.weatherforecast.domain.CurrentWeather
import com.weather.weatherforecast.domain.GetWeatherUseCase
import com.weather.weatherforecast.domain.Weather
import com.weather.weatherforecast.domain.WeatherRepository
import com.weather.weatherforecast.presentation.WeatherViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherUseCase
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {

        repository = mockk()

        useCase = GetWeatherUseCase(repository)

        viewModel = WeatherViewModel(
            useCase,
            repository
        )
    }

    @Test
    fun loadWeatherUpdatesUiState() = runTest {

        val weather = Weather(
            current = CurrentWeather(
                temperature = 28.0,
                weatherCode = 0
            ),
            hourly = emptyList(),
            daily = emptyList()
        )

        coEvery {
            repository.refreshWeather(any(), any(), any())
        } just runs

        every {
            repository.getWeather(any())
        } returns flowOf(weather)

        viewModel.loadWeather(
            city = "Hyderabad",
            latitude = 17.38,
            longitude = 78.48
        )

        advanceUntilIdle()

        assertEquals(
            28.0,
            viewModel.uiState.value.weather?.current?.temperature!!,
            0.0
        )
    }
}