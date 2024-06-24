import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import core.presentation.designsystem.theme.WeatherTheme
import custom.location.LocationRequester
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import core.domain.model.rememberWeatherAppState
import core.presentation.compose.AppScaffold
import core.presentation.navigation.AppNavHost
import kotlinx.coroutines.launch
import okio.FileSystem

private object WeatherKoinComponent : KoinComponent {
    val locationRequester: LocationRequester by inject()
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherApp() {
    setSingletonImageLoaderFactory {
        getAsyncImageLoader(it)
    }
    val weatherAppState = rememberWeatherAppState(
        locationRequester = WeatherKoinComponent.locationRequester,
    )
    val scope = weatherAppState.coroutineScope
    val snackbarHostState = weatherAppState.snackbarHostState
    WeatherTheme {
        AppScaffold(
            snackBarHostState = snackbarHostState
        ) { padding ->
            AppNavHost(
                modifier = Modifier.padding(padding),
                onShowSnackBar = { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                },
                weatherAppState = weatherAppState
            )
        }
    }
}

private fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

private fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024)
        .build()
}