package features.weather.domain.source.remote

import core.domain.model.weather.Weather
import core.domain.model.weather.WeatherRequest
import core.util.AppResult

interface WeatherRemoteDataSource {
    suspend fun getWeatherResponse(
        data: WeatherRequest,
        unit: String,
        format: String
    ): AppResult<Weather>
}