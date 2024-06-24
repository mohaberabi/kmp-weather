package features.settings.data.source.local

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import core.domain.model.AppLocation
import core.util.DispatcherProvider
import core.util.PrefsDataStore
import features.settings.domain.source.local.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SettingsDataStore(
    private val dataStore: PrefsDataStore,
    private val dispatchers: DispatcherProvider,
) : SettingsLocalDataSource {
    companion object {
        private const val DEFAULT_LAT = 31.20220
        private const val DEFAULT_LNG = 31.20220
        private const val DEFAULT_LANG = "ENGLISH"
        private const val DEFAULT_UNIT = "METRIC"
        private const val DEFAULT_FORMAT = "TWELVE"
        private val langKey by lazy { stringPreferencesKey("langKey") }
        private val unitKey by lazy { stringPreferencesKey("unitKey") }
        private val latKey by lazy { doublePreferencesKey("latKey") }
        private val lngKey by lazy { doublePreferencesKey("lngKey") }
        private val formatKey by lazy { stringPreferencesKey("formatKey") }
        private val excludedKey by lazy { stringSetPreferencesKey("excludedKey") }

    }

    override suspend fun setLanguage(lang: String) {

        withContext(dispatchers.io) {
            try {
                set(langKey, lang)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }

    }

    override fun getLanguage(): Flow<String> = get(langKey, DEFAULT_LANG).flowOn(dispatchers.io)

    override suspend fun setUnit(unit: String) {
        withContext(dispatchers.io) {
            try {
                set(unitKey, unit)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun getUnit(): Flow<String> = get(unitKey, DEFAULT_UNIT).flowOn(dispatchers.io)

    override suspend fun setExcludedData(excluded: List<String>) {
        return withContext(dispatchers.io) {
            try {
                set(excludedKey, excluded.toSet())
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun getExcludedData(): Flow<List<String>> =
        get(excludedKey, setOf()).map { it.toList() }.flowOn(dispatchers.io)

    override suspend fun setFormat(format: String) {
        withContext(dispatchers.io) {
            try {
                set(formatKey, format)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun getFormat(): Flow<String> = get(formatKey, DEFAULT_FORMAT).flowOn(dispatchers.io)

    override suspend fun saveLastLocation(location: AppLocation) {
        withContext(dispatchers.io) {
            try {
                set(latKey, location.lat)
                set(lngKey, location.lng)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun getLastLocation(): Flow<AppLocation> =
        get(latKey, DEFAULT_LAT)
            .combine(get(lngKey, DEFAULT_LNG)) { lat, lng ->
                AppLocation(
                    lat = lat,
                    lng = lng
                )
            }.flowOn(dispatchers.io)

    private suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    private fun <T> get(key: Preferences.Key<T>, default: T): Flow<T> {
        return dataStore.data.map { settings ->
            settings[key] ?: default
        }
    }
}