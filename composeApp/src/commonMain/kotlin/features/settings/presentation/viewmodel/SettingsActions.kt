package features.settings.presentation.viewmodel

import core.domain.model.AppLang
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import features.settings.presentation.screen.SettingsItem

sealed interface SettingsActions {


    data object SaveSettings : SettingsActions


    data class OnShowBottomSheet(val item: SettingsItem) : SettingsActions
    data class LanguageChanged(val appLang: AppLang) : SettingsActions

    data class UnitChanged(val unit: MeasuringUnit) : SettingsActions
    data class ClockFormatChanged(val format: ClockFormat) : SettingsActions

    data class ExcludedChanged(val excluded: ExcludedData) : SettingsActions
    data object OnDismissBottomSheet : SettingsActions

}