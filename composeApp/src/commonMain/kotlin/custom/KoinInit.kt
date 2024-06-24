package custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import core.di.appModule
import custom.datastore.datastoreModule
import custom.location.locationModule
import features.settings.di.settingsModule
import features.weather.di.weatherModule
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.DefinitionOptions
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

expect class KoinInit {
    fun init(
        vararg customModules: Module = arrayOf<Module>(
            appModule,
            locationModule,
            weatherModule,
            datastoreModule,
            settingsModule,
        )
    )
}

expect inline fun <reified VM : ViewModel> Module.platformViewModel(
    crossinline viewmodel: Scope.() -> VM,
): KoinDefinition<VM>

