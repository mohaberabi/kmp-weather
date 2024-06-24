package features.weather.domain.repository

import core.domain.model.weather.Weather
import core.domain.model.weather.WeatherRequest
import core.util.AppResult

interface WeatherRepository {
    suspend fun getWeather(
        request: WeatherRequest,
    ): AppResult<Weather>
}