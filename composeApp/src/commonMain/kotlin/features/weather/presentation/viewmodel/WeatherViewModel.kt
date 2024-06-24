package features.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.AppLocation
import core.domain.model.weather.WeatherRequest
import core.util.AppResult
import core.util.UiText
import core.util.asUiText
import custom.location.LocationProvider
import features.settings.domain.repository.SettingsRepository
import features.weather.domain.repository.WeatherRepository
import kmp_weather.composeapp.generated.resources.Res
import kmp_weather.composeapp.generated.resources.unknown_error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()


    init {

        settingsRepository.getSettingsData().map { settings ->
            _state.update {
                it.copy(
                    unit = settings.measuringUnit,
                    language = settings.lang,
                    excludedData = settings.excludes,
                    timeFormat = settings.clockFormat
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getLocation() {
        _state.update { it.copy(status = WeatherStatus.LOADING) }
        viewModelScope.launch {
            when (val locationRes = locationProvider.getLocation()) {
                is AppResult.Done -> {
                    val location = locationRes.data
                    getWeather(location)
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            status = WeatherStatus.ERROR,
                            error = UiText.StringRes(Res.string.unknown_error)
                        )
                    }
                }
            }
        }
    }

    fun action(action: WeatherActions) {
        when (action) {
            WeatherActions.OnLocationGranted -> getLocation()
        }
    }


    private fun getWeather(
        location: AppLocation,
    ) {


        viewModelScope.launch {
            val stateVal = _state.value
            val request = WeatherRequest(
                lat = location.lat,
                lon = location.lng,
                lang = stateVal.language.code,
                units = stateVal.unit.value,
            )
            when (val res = weatherRepository.getWeather(request)) {
                is AppResult.Done -> {
                    _state.update {
                        it.copy(
                            status = WeatherStatus.POPULATED,
                            weather = res.data
                        )
                    }
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(
                            status = WeatherStatus.ERROR,
                            error = res.error.asUiText()
                        )
                    }
                }
            }
        }
    }
}
