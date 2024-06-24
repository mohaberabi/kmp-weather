package features.weather.data.source.remote.dto

import core.domain.model.weather.CurrentWeather
import core.domain.model.weather.DailyWeather
import core.domain.model.weather.HourlyWeather
import core.domain.model.weather.MeasuringUnit
import core.domain.model.weather.Temperature
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.Weather
import core.domain.model.weather.WeatherInfo
import core.util.const.EndPoints
import core.util.extensions.formatTimeMillis
import kotlin.math.roundToInt

private const val DEFAULT_DATE_FORMAT = "EEEE dd/M"

fun WeatherResponse.toWeather(
    unit: String,
    format: String
): Weather {
    return Weather(
        current = current?.toCurrentWeather(unit),
        hourly = hourly?.map { it.toHourlyWeather(format = format) },
        daily = daily?.map { it.toDailyWeather(unit = unit) }
    )
}

fun HourlyWeatherResponse.toHourlyWeather(
    format: String
): HourlyWeather {
    val formatPattern = when (format) {
        ClockFormat.TWELVE.value -> "h:mm a"
        ClockFormat.TWELVE.value -> "HH:SS"
        else -> "HH:SS"
    }
    return HourlyWeather(
        forecastedTime = forecastedTime.formatTimeMillis(formatPattern),
        temperature = formatTemperatureValue(temperature, formatPattern),
        weather = weather.map { it.toWeatherInfo() }
    )
}

fun WeatherInfoResponse.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        id = id,
        description = description,
        icon = "${EndPoints.ICON_END_POINT}${icon}@2x.png",
        main = main
    )
}

fun CurrentWeatherResponse.toCurrentWeather(
    unit: String
): CurrentWeather {
    return CurrentWeather(
        temperature = formatTemperatureValue(temperature, unit),
        feelsLike = formatTemperatureValue(temperature, unit),
        weather = weather.map { it.toWeatherInfo() }
    )
}


fun DailyWeatherResponse.toDailyWeather(unit: String): DailyWeather {
    return DailyWeather(forecastedTime = forecastedTime.formatTimeMillis(DEFAULT_DATE_FORMAT),
        temperature = temperature.toTemperature(unit),
        weather = weather.map { it.toWeatherInfo() }
    )
}

fun TemperatureResponse.toTemperature(
    unit: String,
): Temperature {
    return Temperature(
        max = formatTemperatureValue(max, unit),
        min = formatTemperatureValue(max, unit)
    )
}


private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"

private fun getUnitSymbols(unit: String) = when (unit) {
    MeasuringUnit.METRIC.value -> MeasuringUnit.METRIC.label
    MeasuringUnit.IMPERIAL.value -> MeasuringUnit.IMPERIAL.label
    MeasuringUnit.STANDARD.value -> MeasuringUnit.STANDARD.label
    else -> "N/A"
}

