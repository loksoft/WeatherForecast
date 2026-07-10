package com.weather.weatherforecast.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.loadWeather(
            city = "Singapore",
            latitude = 22.0,
            longitude = 79.0
        )
    }

    Scaffold {

        HomeContent(
            padding = it,
            uiState = uiState
        )
    }
}

@Composable
private fun HomeContent(
    padding: PaddingValues,
    uiState: WeatherUiState
) {

    when {

        uiState.isLoading -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        uiState.weather != null -> {

            val weather = uiState.weather

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                CurrentWeatherCard(
                    temperature = weather.current.temperature,
                    weatherCode = weather.current.weatherCode
                )

                HourlyForecast(weather.hourly)

                DailyForecast(weather.daily)
            }
        }
    }
}