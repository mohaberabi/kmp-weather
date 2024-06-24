package custom.location

import org.koin.dsl.module


actual val locationModule = module {
    single<LocationProvider> {
        LocationProvider(get(), get())
    }
    single<LocationRequester> {
        LocationRequester()
    }
}