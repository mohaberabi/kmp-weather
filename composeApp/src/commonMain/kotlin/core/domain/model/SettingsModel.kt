package core.domain.model

import core.domain.model.weather.ClockFormat
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit

data class SettingsModel(
    val lang: AppLang = AppLang.ENGLISH,
    val excludes: List<ExcludedData> = listOf(),
    val clockFormat: ClockFormat = ClockFormat.TWELVE,
    val measuringUnit: MeasuringUnit = MeasuringUnit.METRIC
)