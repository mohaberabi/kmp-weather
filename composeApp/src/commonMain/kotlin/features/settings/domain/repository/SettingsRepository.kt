package features.settings.domain.repository

import core.domain.model.AppLang
import core.domain.model.AppLocation
import core.domain.model.SettingsModel
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import core.domain.model.weather.ClockFormat
import core.util.AppResult
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLanguage(lang: AppLang): AppResult<Unit>
    fun getLanguage(): Flow<AppLang>
    suspend fun setUnit(unit: MeasuringUnit): AppResult<Unit>
    fun getUnit(): Flow<MeasuringUnit>
    suspend fun setExcludedData(excluded: List<ExcludedData>): AppResult<Unit>
    fun getExcludedData(): Flow<List<ExcludedData>>
    suspend fun setFormat(format: ClockFormat): AppResult<Unit>
    fun getFormat(): Flow<ClockFormat>
    suspend fun saveLastLocation(location: AppLocation): AppResult<Unit>
    fun getLastLocation(): Flow<AppLocation>
    fun getSettingsData(): Flow<SettingsModel>
}