package features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.AppLang
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import core.util.AppResult
import core.util.asUiText
import features.settings.domain.repository.SettingsRepository
import features.settings.presentation.screen.SettingsItem
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {


    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()


    init {
        settingsRepository.getSettingsData()
            .map { settings ->
                _state.update { it.copy(settingsData = settings) }
            }.launchIn(viewModelScope)
    }


    fun onAction(action: SettingsActions) {
        when (action) {
            is SettingsActions.SaveSettings -> onSave()
            is SettingsActions.ClockFormatChanged -> clockFormatChanged(action.format)
            is SettingsActions.ExcludedChanged -> excludesChanged(action.excluded)
            is SettingsActions.LanguageChanged -> languageChanged(action.appLang)
            is SettingsActions.UnitChanged -> measuringUnitChanged(action.unit)
            is SettingsActions.OnShowBottomSheet -> showBottomSheet(action.item)
            SettingsActions.OnDismissBottomSheet -> hideBottomSheet()
        }
    }


    private fun hideBottomSheet() =
        _state.update { it.copy(showBottomSheet = false, bottomSheetType = null) }

    private fun showBottomSheet(item: SettingsItem) =
        _state.update { it.copy(showBottomSheet = true, bottomSheetType = item) }

    private fun onSave() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            val res = viewModelScope.async {
                val settings = _state.value.settingsData
                when (_state.value.bottomSheetType) {
                    SettingsItem.LANGUAGE -> settingsRepository.setLanguage(settings.lang)
                    SettingsItem.Units -> settingsRepository.setUnit(settings.measuringUnit)
                    SettingsItem.CLOCK_FORMAT -> settingsRepository.setFormat(settings.clockFormat)
                    SettingsItem.Exclude -> settingsRepository.setExcludedData(settings.excludes)
                    null -> AppResult.Done(Unit)
                }
            }.await()
            mapResultToEvent(res)
        }
    }

    private fun excludesChanged(excludedData: ExcludedData) {
        val excluded = _state.value.settingsData.excludes.toMutableSet()
            .apply {
                if (contains(excludedData)) remove(excludedData) else {
                    add(excludedData)
                }
            }
        _state.update { it.copy(settingsData = it.settingsData.copy(excludes = excluded.toList())) }
    }

    private fun languageChanged(lang: AppLang) =
        _state.update { it.copy(settingsData = it.settingsData.copy(lang = lang)) }


    private fun clockFormatChanged(format: ClockFormat) =
        _state.update { it.copy(settingsData = it.settingsData.copy(clockFormat = format)) }

    private fun measuringUnitChanged(unit: MeasuringUnit) =
        _state.update { it.copy(settingsData = it.settingsData.copy(measuringUnit = unit)) }


    private suspend fun mapResultToEvent(result: AppResult<*>) {
        _state.update { it.copy(loading = false) }
        when (result) {
            is AppResult.Done -> _state.update { it.copy(showBottomSheet = false) }
            is AppResult.Error -> _event.send(SettingsEvent.Error(result.error.asUiText()))
        }
    }
}