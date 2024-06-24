package custom.location

import androidx.compose.runtime.Composable
import core.domain.model.PermissionCallBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import core.domain.model.PermissionResult
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted

actual class LocationRequester {
    private val locationManager = CLLocationManager()
    lateinit var locationRequestCallBack: PermissionCallBack

    @Composable
    actual fun registerLocationRequest(
        onGranted: () -> Unit,
        onDenied: () -> Unit,
    ) {

        if (!::locationRequestCallBack.isInitialized) {
            locationRequestCallBack = PermissionCallBack(
                onGranted = onGranted,
                onDenied = onDenied,
            )
        }
    }

    @Composable
    actual fun rememberLocationPermission(): Boolean {
        val allowed by remember {
            mutableStateOf(locationManager.authorizationStatus().mapToAppPermission().isGranted)
        }
        return allowed
    }

    actual fun launchLocationRequest() {
        locationManager.requestAlwaysAuthorization()
        val permissionResult =
            locationManager.authorizationStatus().mapToAppPermission()
        if (permissionResult.isUnknown) {
            locationManager.requestAlwaysAuthorization()
        } else if (permissionResult.isGranted) {
            locationRequestCallBack.onGranted.invoke()
        } else {
            locationRequestCallBack.onDenied.invoke()
        }
    }

    private fun CLAuthorizationStatus.mapToAppPermission(): PermissionResult {
        return when (this) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse,
            kCLAuthorizationStatusRestricted
            -> PermissionResult.GRANTED

            kCLAuthorizationStatusNotDetermined -> PermissionResult.UNKNOWN
            kCLAuthorizationStatusDenied -> PermissionResult.DENIED
            else -> PermissionResult.UNKNOWN
        }

    }
}