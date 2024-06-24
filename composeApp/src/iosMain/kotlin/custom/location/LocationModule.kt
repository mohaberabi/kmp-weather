package custom.location

import org.koin.dsl.module


actual val locationModule = module {
    single<LocationProvider> {
        LocationProvider(get())
    }
    single<LocationRequester> {
        LocationRequester()
    }
}