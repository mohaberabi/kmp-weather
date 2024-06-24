package features.settings.presentation.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import core.domain.model.AppLang
import core.domain.model.weather.ClockFormat
import core.domain.model.weather.ExcludedData
import core.domain.model.weather.MeasuringUnit
import core.presentation.compose.AppBottomSheet
import core.presentation.compose.AppButton
import core.presentation.compose.AppCheckBox
import core.presentation.compose.AppRadio
import core.presentation.compose.AppScaffold
import core.presentation.compose.PrimaryAppBar
import core.presentation.compose.SimpleListItem
import core.presentation.designsystem.theme.Spacing
import core.util.koinViewModel
import features.settings.presentation.viewmodel.SettingsActions
import features.settings.presentation.viewmodel.SettingsState
import features.settings.presentation.viewmodel.SettingsViewModel
import kmp_weather.composeapp.generated.resources.Res
import kmp_weather.composeapp.generated.resources.clock
import kmp_weather.composeapp.generated.resources.exclude
import kmp_weather.composeapp.generated.resources.lang
import kmp_weather.composeapp.generated.resources.save
import kmp_weather.composeapp.generated.resources.settings
import kmp_weather.composeapp.generated.resources.units
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class SettingsItem(
    val headline: StringResource,
    val icon: ImageVector,
) {
    LANGUAGE(
        Res.string.lang,
        Icons.Default.Place,
    ),
    Units(
        Res.string.units,
        Icons.Default.Settings,
    ),
    CLOCK_FORMAT(
        Res.string.clock,
        Icons.Default.Favorite,
    ),
    Exclude(
        Res.string.exclude,
        Icons.Rounded.Clear,
    ),

}

@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {


    val state by viewModel.state.collectAsState()
    SettingsScreen(
        modifier = modifier,
        onGoBack = onGoBack,
        state = state,
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    state: SettingsState,
    onAction: (SettingsActions) -> Unit
) {
    AppScaffold(
        modifier = modifier,
        topAppBar = {
            PrimaryAppBar(
                showBackButton = true,
                onBackClick = onGoBack,
                isCenter = false,
                titleContent = {
                    Text(
                        stringResource(Res.string.settings),
                        style = MaterialTheme.typography
                            .headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                            )
                    )
                }

            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.lg)
                .scrollable(rememberScrollState(), Orientation.Vertical),
        ) {

            SettingsItem.entries.forEach { item ->
                SimpleListItem(
                    leading = item.icon,
                    trailing = when (item) {
                        SettingsItem.LANGUAGE -> state.settingsData.lang.langName
                        SettingsItem.Units -> state.settingsData.measuringUnit.label
                        SettingsItem.CLOCK_FORMAT -> state.settingsData.clockFormat.value
                        SettingsItem.Exclude -> state.settingsData.excludes.joinToString(",") { it.name }
                    },
                    headline = stringResource(item.headline),
                    onClick = {
                        onAction(SettingsActions.OnShowBottomSheet(item = item))
                    }
                )
            }
        }

        if (state.showBottomSheet) {
            AppBottomSheet(
                modifier = Modifier.padding(bottom = Spacing.xlg),
                onDismiss = {
                    onAction(SettingsActions.OnDismissBottomSheet)
                },
            ) {

                Text(
                    when (state.bottomSheetType) {
                        SettingsItem.LANGUAGE -> Res.string.lang
                        SettingsItem.Units -> Res.string.units
                        SettingsItem.CLOCK_FORMAT -> Res.string.clock
                        else -> Res.string.exclude
                    }.let { stringResource(it) },
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                when (state.bottomSheetType) {
                    SettingsItem.LANGUAGE -> AppRadio(
                        items = AppLang.entries,
                        selected = { item -> item == state.settingsData.lang },
                        label = { item -> item!!.langName },
                        onChanged = { onAction(SettingsActions.LanguageChanged(it)) }
                    )

                    SettingsItem.Units -> AppRadio(
                        items = MeasuringUnit.entries,
                        selected = { item -> item == state.settingsData.measuringUnit },
                        label = { item -> item!!.label },
                        onChanged = { onAction(SettingsActions.UnitChanged(it)) }
                    )

                    SettingsItem.CLOCK_FORMAT -> AppRadio(
                        items = ClockFormat.entries,
                        selected = { item -> item == state.settingsData.clockFormat },
                        label = { item -> item!!.value },
                        onChanged = { onAction(SettingsActions.ClockFormatChanged(it)) }
                    )

                    SettingsItem.Exclude -> AppCheckBox(
                        items = ExcludedData.entries,
                        label = { item -> item!!.value },
                        onChanged = { onAction(SettingsActions.ExcludedChanged(it)) },
                        selected = { item -> state.settingsData.excludes.contains(item) })

                    null -> Unit
                }
                AppButton(
                    loading = state.loading,
                    label = stringResource(Res.string.save),
                    onClick = { onAction(SettingsActions.SaveSettings) },
                )
            }

        }


    }
}


//@Composable
//fun <Any> SettingsBottomSheet(
//    modifier: Modifier = Modifier,
//    loading: Boolean = false,
//    items: List<Any>,
//    current: List<Any>,
//    isMultipleChoice: Boolean = false,
//    onItemClick: (Any) -> Unit,
//    onSave: () -> Unit,
//    title: String = "",
//    onDismiss: () -> Unit
//) {
//
//    AppBottomSheet(
//        modifier = modifier,
//        onDismiss = onDismiss,
//    ) {
//        Text(
//            title,
//            style = MaterialTheme.typography
//                .displayMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                )
//        )
//        if (isMultipleChoice) {
//            AppRadio(
//                items = items,
//                onChanged = onItemClick,
//                selected = { item -> current.contains(item) }
//            )
//        } else {
//            AppCheckBox(
//                onChanged = onItemClick,
//                items = items,
//                selected = { item -> current.contains(item) }
//            )
//        }
//        AppButton(
//            loading = loading,
//            onClick = onSave,
//        )
//    }
//
//}

//SettingsBottomSheet(
//                loading = state.loading,
//                onSave = { onAction(SettingsActions.SaveSettings(SettingsItem.Units)) },
//                items = when (state.bottomSheetType) {
//                    SettingsItem.LANGUAGE -> AppLang.entries
//                    SettingsItem.Units -> MeasuringUnit.entries
//                    SettingsItem.CLOCK_FORMAT -> ClockFormat.entries
//                    SettingsItem.Exclude -> ExcludedData.entries
//                    null -> listOf()
//                },
//                onItemClick = { item ->
//                    when (state.bottomSheetType) {
//                        SettingsItem.LANGUAGE -> onAction(SettingsActions.LanguageChanged(item as AppLang))
//                        SettingsItem.Units ->
//                        SettingsItem.CLOCK_FORMAT ->
//                        SettingsItem.Exclude ->
//                        null -> Unit
//                    }
//                },
//                onDismiss = { onAction(SettingsActions.OnDismissBottomSheet) },
//                current = when (state.bottomSheetType) {
//                    SettingsItem.LANGUAGE -> listOf(state.settingsData.lang)
//                    SettingsItem.Units -> listOf(state.settingsData.measuringUnit)
//                    SettingsItem.CLOCK_FORMAT -> listOf(state.settingsData.clockFormat)
//                    SettingsItem.Exclude -> state.settingsData.excludes
//                    null -> listOf()
//                },
//            )