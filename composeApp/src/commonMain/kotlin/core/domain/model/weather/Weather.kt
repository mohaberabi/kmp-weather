package core.domain.model.weather

import core.util.const.EndPoints
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Weather(
    val current: CurrentWeather?,
    val hourly: List<HourlyWeather>?,
    val daily: List<DailyWeather>?
)

data class CurrentWeather(
    val temperature: String,
    val feelsLike: String,
    val weather: List<WeatherInfo>
)

data class HourlyWeather(
    val forecastedTime: String,
    val temperature: String,
    val weather: List<WeatherInfo>
)

data class DailyWeather(
    val forecastedTime: String,
    val temperature: Temperature,
    val weather: List<WeatherInfo>
)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Temperature(
    val min: String,
    val max: String,
)

@Serializable
data class WeatherRequest(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("lang")
    val lang: String = "en",
    @SerialName("units")
    val units: String = "C",
    @SerialName("exclude")
    val exclude: String? = null,
    @SerialName("appid")
    val appid: String = EndPoints.APP_ID
)

fun WeatherRequest.toMap(): Map<String, Any?> {
    return mapOf<String, Any?>(
        "appid" to appid,
        "lat" to lat,
        "lon" to lon,
        "units" to units,
        "exclude" to exclude,
        "lang" to lang,
    )
}