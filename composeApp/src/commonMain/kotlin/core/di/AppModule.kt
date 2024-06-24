package core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.util.DefaultDispatcherProvider
import core.util.DispatcherProvider
import core.util.HttpClientFactory
import custom.location.LocationRequester
import io.ktor.client.HttpClient
import org.koin.dsl.module


val appModule = module {

    single<HttpClient> {
        HttpClientFactory().build()
    }

    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }


}