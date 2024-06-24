package core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import core.domain.model.WeatherAppState
import features.settings.presentation.screen.SettingsScreenRoot
import features.weather.presentation.screen.WeatherScreenRoot

object AppRoutes {
    const val WEATHER = "weatherRoute"
    const val SETTINGS = "settings"

}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startRoute: String = AppRoutes.WEATHER,
    onShowSnackBar: (String) -> Unit,
    weatherAppState: WeatherAppState,
) {
    val locationRequester = weatherAppState.locationRequester
    val navController = weatherAppState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startRoute
    ) {
        composable(AppRoutes.WEATHER) {
            WeatherScreenRoot(
                onGoSettings = { navController.navigate(AppRoutes.SETTINGS) },
                locationRequester = locationRequester,
            )
        }
        composable(AppRoutes.SETTINGS) {
            SettingsScreenRoot(
                onShowSnackBar = onShowSnackBar,
                onGoBack = { navController.popBackStack() }
            )
        }
    }
}