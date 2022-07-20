package poran.cse.myweatherapp.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import poran.cse.myweatherapp.data.remote.WeatherDataDto
import poran.cse.myweatherapp.data.remote.WeatherDto
import poran.cse.myweatherapp.domain.weather.WeatherData
import poran.cse.myweatherapp.domain.weather.WeatherInfo
import poran.cse.myweatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexWeatherData(
    val index: Int,
    val data: WeatherData
)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMapper(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperature[index]
        val windSpeed = windSpeed[index]
        val pressure = pressure[index]
        val weatherCode = weatherCode[index]
        val humidity = relativeHumidity[index]
        val data = WeatherData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperatureCelsius = temperature,
            windSpeed = windSpeed,
            pressureLevel = pressure,
            humidity = humidity,
            weatherType = WeatherType.fromWMO(weatherCode.toInt()),
        )
        IndexWeatherData(
            index,
            data
        )
    }.groupBy {
        it.index/24
    }
        .mapValues {
            it.value.map { it.data }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherDataDto.toWeatherDataMapper()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataMap,
        currentWeatherData
    )


}