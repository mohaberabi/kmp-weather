package features.weather.data.source.remote.dto

import core.domain.model.weather.Weather
import core.domain.model.weather.WeatherRequest
import core.domain.model.weather.toMap
import core.util.AppResult
import core.util.const.EndPoints
import core.util.extensions.get
import features.weather.domain.source.remote.WeatherRemoteDataSource
import io.ktor.client.HttpClient

class KtorWeatherRemoteDataSource(
    private val httpClient: HttpClient,
) : WeatherRemoteDataSource {
    override suspend fun getWeatherResponse(
        data: WeatherRequest,
        unit: String,
        format: String,
    ): AppResult<Weather> {
        val response = httpClient.get<WeatherResponse>(
            path = EndPoints.WEATHER_END_POINT,
            queryParams = data.toMap(),
        )
        return when (response) {
            is AppResult.Done -> AppResult.Done(
                response.data.toWeather(
                    unit = unit,
                    format = format
                )
            )

            is AppResult.Error -> AppResult.Error(response.error)
        }

    }
}