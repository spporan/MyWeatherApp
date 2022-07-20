package poran.cse.myweatherapp.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDataDto(
    @Json(name = "pressure_msl")
    val pressure: List<Double>,
    @Json(name = "relativehumidity_2m")
    val relativeHumidity: List<Double>,
    @Json(name = "temperature_2m")
    val temperature: List<Double>,
    @Json(name = "time")
    val time: List<String>,
    @Json(name = "weathercode")
    val weatherCode: List<Double>,
    @Json(name = "windspeed_10m")
    val windSpeed: List<Double>
)