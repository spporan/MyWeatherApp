package poran.cse.myweatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import poran.cse.myweatherapp.data.mappers.toWeatherInfo
import poran.cse.myweatherapp.data.remote.WeatherApi
import poran.cse.myweatherapp.domain.repository.WeatherRepository
import poran.cse.myweatherapp.domain.util.Resource
import poran.cse.myweatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat, long).toWeatherInfo()
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }
}