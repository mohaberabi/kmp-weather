package features.weather.di

import custom.platformViewModel
import features.weather.data.repository.DefaultWeatherRepository
import features.weather.data.source.remote.dto.KtorWeatherRemoteDataSource
import features.weather.domain.repository.WeatherRepository
import features.weather.domain.source.remote.WeatherRemoteDataSource
import features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.dsl.module


val weatherModule = module {
    single<WeatherRemoteDataSource> {
        KtorWeatherRemoteDataSource(get())
    }


    single<WeatherRepository> {
        DefaultWeatherRepository(get())
    }

    platformViewModel<WeatherViewModel> { WeatherViewModel(get(), get(), get()) }

}


