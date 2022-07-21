package poran.cse.myweatherapp.domain.repository

import poran.cse.myweatherapp.domain.util.Resource
import poran.cse.myweatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}