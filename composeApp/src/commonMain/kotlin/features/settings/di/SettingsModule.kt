package features.settings.di

import custom.platformViewModel
import features.settings.data.repository.DefaultSettingsRepository
import features.settings.data.source.local.SettingsDataStore
import features.settings.domain.repository.SettingsRepository
import features.settings.domain.source.local.SettingsLocalDataSource
import features.settings.presentation.viewmodel.SettingsViewModel
import org.koin.dsl.module


val settingsModule = module {
    single<SettingsLocalDataSource> { SettingsDataStore(get(), get()) }
    single<SettingsRepository> { DefaultSettingsRepository(get()) }
    platformViewModel<SettingsViewModel> { SettingsViewModel(get()) }
}