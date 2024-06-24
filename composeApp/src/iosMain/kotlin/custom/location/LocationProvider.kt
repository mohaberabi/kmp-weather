package custom.location


import core.domain.model.AppLocation
import core.util.AppResult
import core.util.CommonError
import core.util.DispatcherProvider
import core.util.LocationError
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.concurrent.AtomicReference
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationProvider(
    private val dispatchers: DispatcherProvider,
) {

    companion object {
        private val ACCURACY = kCLLocationAccuracyBest
        private val DISTANCE_FILTER = kCLDistanceFilterNone
    }

    private val locationManager: CLLocationManager = CLLocationManager()
    private val locationDelegate = LocationDelegate()

    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
        var onLocationUpdate: ((AppLocation?) -> Unit)? = null
        override fun locationManager(
            manager: CLLocationManager,
            didUpdateLocations: List<*>
        ) {
            didUpdateLocations.firstOrNull()?.let {
                val location = it as CLLocation
                onLocationUpdate?.invoke(location.toAppLocation())
            }
        }


        override fun locationManager(
            manager: CLLocationManager,
            didFailWithError: NSError
        ) {
            onLocationUpdate?.invoke(null)
        }
    }

    actual suspend fun getLocation(): AppResult<AppLocation> {
        return withContext(dispatchers.io) {
            try {
                val cllLocation = locationManager.location
                cllLocation?.let {
                    AppResult.Done(it.toAppLocation())
                } ?: AppResult.Error(LocationError.ERROR_GETTING_LOCATION)
            } catch (e: Exception) {
                AppResult.Error(LocationError.ERROR_GETTING_LOCATION)
            }
        }
    }

    private fun initializeLocationManager() {
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = ACCURACY
        locationManager.distanceFilter = DISTANCE_FILTER
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocation.toAppLocation(): AppLocation {
    return coordinate.useContents {
        AppLocation(
            latitude,
            longitude,
        )
    }

}