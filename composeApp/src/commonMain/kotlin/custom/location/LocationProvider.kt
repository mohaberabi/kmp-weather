package custom.location

import core.domain.model.AppLocation
import core.util.AppResult
import org.koin.core.module.Module

expect class LocationProvider {


    suspend fun getLocation(): AppResult<AppLocation>


}


