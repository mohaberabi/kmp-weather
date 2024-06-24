package features.weather.presentation.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import core.domain.model.weather.Weather
import core.presentation.compose.AppLoader
import core.presentation.compose.AppScaffold
import core.presentation.compose.ConditionalBuilder
import core.presentation.compose.ErrorCard
import core.presentation.compose.PrimaryAppBar
import core.presentation.designsystem.theme.Spacing
import core.util.extensions.spannedItem
import core.util.koinViewModel
import custom.location.LocationRequester
import features.weather.presentation.compose.CurrentWeatherCompose
import features.weather.presentation.compose.DailyForecastBuilder
import features.weather.presentation.compose.HourlyForecastComposeBuilder
import features.weather.presentation.viewmodel.WeatherActions
import features.weather.presentation.viewmodel.WeatherState
import features.weather.presentation.viewmodel.WeatherStatus
import features.weather.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel(),
    locationRequester: LocationRequester,
    onGoSettings: () -> Unit,
) {

    locationRequester.registerLocationRequest(
        onGranted = { viewModel.action(WeatherActions.OnLocationGranted) },
        onDenied = {
        },
    )
    val locationGranted = locationRequester.rememberLocationPermission()
    LaunchedEffect(
        Unit,
    ) {
        locationRequester.launchLocationRequest()
    }

    val state by viewModel.state.collectAsState()
    WeatherScreen(
        modifier = modifier,
        isLocationGranted = locationGranted,
        state = state,
        onAction = viewModel::action,
        onGoSettings = onGoSettings
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    isLocationGranted: Boolean,
    onAction: (WeatherActions) -> Unit,
    state: WeatherState,
    onGoSettings: () -> Unit
) {

    AppScaffold(
        topAppBar = {
            PrimaryAppBar(
                actions = {
                    IconButton(
                        onClick = onGoSettings,
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "settings"
                        )
                    }
                },
                isCenter = false,
                titleContent = {
                    Text(
                        "Weather",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black)
                    )
                },
                showBackButton = false,
            )

        }
    ) { padding ->
        ConditionalBuilder(
            modifier = modifier.padding(padding),
            condition = isLocationGranted,
            onNegative = {

            },
            onPositive = {
                when (state.status) {

                    WeatherStatus.ERROR -> {
                        ErrorCard(
                            errorTitle = state.error,
                            onRetry = {},
                        )
                    }

                    WeatherStatus.POPULATED -> {
                        WeatherPopulated(weather = state.weather!!)
                    }

                    else -> AppLoader()
                }

            }
        )

    }

}

@Composable
fun WeatherPopulated(
    modifier: Modifier = Modifier,
    weather: Weather,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = modifier.padding(Spacing.lg),
    ) {


        spannedItem(1) {
            weather.current?.let {
                CurrentWeatherCompose(currentWeather = it)
            }
        }
        spannedItem(1) {
            weather.hourly?.let {
                HourlyForecastComposeBuilder(items = it)
            }
        }
        spannedItem(1) {
            Spacer(Modifier.height(Spacing.md))
        }
        spannedItem(1) {
            weather.daily?.let {
                DailyForecastBuilder(items = it)
            }
        }
    }
}