package features.settings.data.repository

import core.domain.model.AppLang
import core.domain.model.AppLocation
import core.domain.model.SettingsModel
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import core.domain.model.weather.ClockFormat
import core.domain.model.mapExceptionToAppResult
import core.util.AppResult
import core.util.extensions.toEnum
import features.settings.domain.repository.SettingsRepository
import features.settings.domain.source.local.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DefaultSettingsRepository(
    private val settingsLocalDataSource: SettingsLocalDataSource,
) : SettingsRepository {
    override suspend fun setLanguage(lang: AppLang): AppResult<Unit> {
        return try {
            settingsLocalDataSource.setLanguage(lang.name)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            val error = mapExceptionToAppResult(e)
            AppResult.Error(error)
        }
    }

    override fun getLanguage(): Flow<AppLang> =
        settingsLocalDataSource.getLanguage().map { it.toEnum() }

    override suspend fun setUnit(unit: MeasuringUnit): AppResult<Unit> {
        return try {
            settingsLocalDataSource.setUnit(unit.name)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            val error = mapExceptionToAppResult(e)
            AppResult.Error(error)
        }
    }

    override fun getUnit(): Flow<MeasuringUnit> =
        settingsLocalDataSource.getUnit().map { it.toEnum() }

    override suspend fun setExcludedData(excluded: List<ExcludedData>): AppResult<Unit> {
        return try {
            settingsLocalDataSource.setExcludedData(excluded.map { it.name })
            AppResult.Done(Unit)
        } catch (e: Exception) {
            val error = mapExceptionToAppResult(e)
            AppResult.Error(error)
        }
    }

    override fun getExcludedData(): Flow<List<ExcludedData>> =
        settingsLocalDataSource.getExcludedData().map { list -> list.map { it.toEnum() } }

    override suspend fun setFormat(format: ClockFormat): AppResult<Unit> {
        return try {
            settingsLocalDataSource.setFormat(format.name)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            val error = mapExceptionToAppResult(e)
            AppResult.Error(error)
        }
    }

    override fun getFormat(): Flow<ClockFormat> =
        settingsLocalDataSource.getFormat().map { it.toEnum() }

    override suspend fun saveLastLocation(location: AppLocation): AppResult<Unit> {
        return try {
            settingsLocalDataSource.saveLastLocation(location)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            val error = mapExceptionToAppResult(e)
            AppResult.Error(error)
        }
    }

    override fun getSettingsData(): Flow<SettingsModel> {
        return combine(
            settingsLocalDataSource.getExcludedData(),
            settingsLocalDataSource.getUnit(),
            settingsLocalDataSource.getLanguage(),
            settingsLocalDataSource.getFormat()
        ) { exclud, unit, lang, format ->
            SettingsModel(
                lang = lang.toEnum(),
                clockFormat = format.toEnum(),
                measuringUnit = unit.toEnum(),
                excludes = exclud.map { it.toEnum() }
            )
        }
    }

    override fun getLastLocation(): Flow<AppLocation> = settingsLocalDataSource.getLastLocation()
}