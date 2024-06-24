package custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.core.context.startKoin
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.DefinitionOptions
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.onOptions
import org.koin.core.module.factory
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import kotlin.reflect.KClass


actual class KoinInit {
    actual fun init(
        vararg customModules: Module
    ) {
        startKoin {
            modules(
                *customModules,
            )
        }
    }
}


actual inline fun <reified VM : ViewModel> Module.platformViewModel(
    crossinline viewmodel: Scope.() -> VM,
): KoinDefinition<VM> {
    return factory {
        viewmodel(this)
    }
}

