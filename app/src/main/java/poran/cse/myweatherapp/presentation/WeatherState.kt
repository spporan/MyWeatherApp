package poran.cse.myweatherapp.presentation

import poran.cse.myweatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val currentWeatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
