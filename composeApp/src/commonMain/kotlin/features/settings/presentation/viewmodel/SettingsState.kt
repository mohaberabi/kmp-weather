package features.settings.presentation.viewmodel

import core.domain.model.SettingsModel
import features.settings.presentation.screen.SettingsItem

data class SettingsState(
    val loading: Boolean = false,
    val settingsData: SettingsModel = SettingsModel(),
    val showBottomSheet: Boolean = false,
    val bottomSheetType: SettingsItem? = null
)
