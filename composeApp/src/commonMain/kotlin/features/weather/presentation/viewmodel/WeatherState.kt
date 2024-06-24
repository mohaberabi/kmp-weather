package features.weather.presentation.viewmodel

import core.domain.model.AppLang
import core.domain.model.AppLocation
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.Weather
import core.util.UiText

enum class WeatherStatus {
    INITIAL, LOADING, ERROR, POPULATED;
}

data class WeatherState(
    val status: WeatherStatus = WeatherStatus.INITIAL,
    val location: AppLocation = AppLocation(),
    val language: AppLang = AppLang.ENGLISH,
    val unit: MeasuringUnit = MeasuringUnit.METRIC,
    val timeFormat: ClockFormat = ClockFormat.TWELVE,
    val weather: Weather? = null,
    val error: UiText = UiText.Empty,
    val excludedData: List<ExcludedData> = listOf(),
)
