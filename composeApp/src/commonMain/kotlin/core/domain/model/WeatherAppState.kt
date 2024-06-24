package core.domain.model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import custom.location.LocationRequester
import kotlinx.coroutines.CoroutineScope

data class WeatherAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
    val locationRequester: LocationRequester,
)


@Composable
fun rememberWeatherAppState(
    locationRequester: LocationRequester
): WeatherAppState {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    return remember {
        WeatherAppState(
            coroutineScope = scope,
            snackbarHostState = hostState,
            navController = navController,
            locationRequester = locationRequester
        )
    }
}