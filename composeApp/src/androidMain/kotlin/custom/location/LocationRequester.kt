package custom.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


actual class LocationRequester {


    private lateinit var launcher: ActivityResultLauncher<String>

    @Composable
    actual fun registerLocationRequest(
        onGranted: () -> Unit,
        onDenied: () -> Unit,
    ) {
        launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { granted ->
                if (granted) onGranted() else onDenied()
            }

    }


    actual fun launchLocationRequest() {
        launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    @Composable
    actual fun rememberLocationPermission(): Boolean {
        val context = LocalContext.current
        val allowed by remember {
            mutableStateOf(
                context.locationPermissionGranted()
            )
        }
        return allowed
    }
}


private fun Context.checkPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED


private fun Context.locationPermissionGranted(): Boolean =
    checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)







