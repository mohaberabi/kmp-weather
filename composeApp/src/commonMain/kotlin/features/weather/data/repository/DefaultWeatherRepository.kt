package features.weather.data.repository

import core.domain.model.weather.MeasuringUnit
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.Weather
import core.domain.model.weather.WeatherRequest
import core.util.AppResult
import features.weather.domain.repository.WeatherRepository
import features.weather.domain.source.remote.WeatherRemoteDataSource

class DefaultWeatherRepository(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {
    override suspend fun getWeather(request: WeatherRequest): AppResult<Weather> =
        weatherRemoteDataSource.getWeatherResponse(
            data = request,
            unit = MeasuringUnit.METRIC.value,
            format = ClockFormat.TWELVE.value
        )
}