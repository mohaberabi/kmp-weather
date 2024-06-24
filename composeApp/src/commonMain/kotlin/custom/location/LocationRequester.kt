package custom.location

import androidx.compose.runtime.Composable


expect class LocationRequester {


    @Composable

    fun registerLocationRequest(
        onGranted: () -> Unit,
        onDenied: () -> Unit,
    )


    fun launchLocationRequest()


    @Composable
    fun rememberLocationPermission(): Boolean
}