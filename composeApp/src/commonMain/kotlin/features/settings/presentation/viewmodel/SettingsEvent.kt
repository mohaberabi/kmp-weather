package features.settings.presentation.viewmodel

import core.util.UiText

sealed interface SettingsEvent {


    data class Error(val error: UiText) : SettingsEvent
}