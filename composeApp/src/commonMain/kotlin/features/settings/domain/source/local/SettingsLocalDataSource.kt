package features.settings.domain.source.local

import core.domain.model.AppLocation
import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    suspend fun setLanguage(lang: String)
    fun getLanguage(): Flow<String>
    suspend fun setUnit(unit: String)
    fun getUnit(): Flow<String>
    suspend fun setExcludedData(excluded: List<String>)
    fun getExcludedData(): Flow<List<String>>
    suspend fun setFormat(format: String)
    fun getFormat(): Flow<String>
    suspend fun saveLastLocation(location: AppLocation)
    fun getLastLocation(): Flow<AppLocation>
}