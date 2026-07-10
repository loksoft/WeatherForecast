package com.weather.weatherforecast.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weather.weatherforecast.domain.DailyWeather
import com.weather.weatherforecast.domain.HourlyWeather

@Composable
fun CurrentWeatherCard(
    temperature: Double,
    weatherCode: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Weather Code : $weatherCode"
            )
        }
    }
}

@Composable
fun HourlyForecast(
    hourly: List<HourlyWeather>
) {

    LazyRow {

        items(hourly) { item ->

            Card(
                modifier = Modifier.padding(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text(item.time)

                    Text("${item.temperature}°C")
                }
            }
        }
    }
}

@Composable
fun DailyForecast(
    daily: List<DailyWeather>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        daily.forEach {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(it.date)

                Text(
                    "${it.minTemperature}° / ${it.maxTemperature}°"
                )
            }
        }
    }
}