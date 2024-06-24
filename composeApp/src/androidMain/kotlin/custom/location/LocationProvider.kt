package custom.location

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.internal.service.Common
import com.google.android.gms.location.LocationServices
import core.domain.model.AppLocation
import core.util.AppResult
import core.util.CommonError
import core.util.DispatcherProvider
import core.util.LocationError
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


actual class LocationProvider(
    private val dispatchers: DispatcherProvider,
    private val context: Context,
) {

    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }


    @Suppress("missingPermission")
    actual suspend fun getLocation(): AppResult<AppLocation> {
        return withContext(dispatchers.io) {
            suspendCoroutine { cont ->
                client.lastLocation.addOnSuccessListener { loc ->
                    loc?.let {
                        cont.resume(
                            AppResult.Done(
                                AppLocation(
                                    lat = loc.latitude,
                                    lng = loc.longitude
                                )
                            )
                        )
                    }
                }
            } ?: run {
                AppResult.Error(LocationError.ERROR_GETTING_LOCATION)
            }
        }
    }
}