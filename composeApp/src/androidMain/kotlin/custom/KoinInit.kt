package custom

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import core.di.appModule
import features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.context.startKoin
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.DefinitionOptions
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.factory
import org.koin.dsl.module
import kotlin.reflect.KClass


actual class KoinInit(
    private val context: Context,
) {

    actual fun init(
        vararg customModules: Module
    ) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(
                *customModules,
            )
        }
    }
}


actual inline fun <reified VM : ViewModel> Module.platformViewModel(
    crossinline viewmodel: Scope.() -> VM,
): KoinDefinition<VM> {
    return this.viewModel {
        viewmodel(this)
    }
}

