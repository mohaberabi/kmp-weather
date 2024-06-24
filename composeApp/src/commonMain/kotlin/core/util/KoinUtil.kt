package core.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.currentKoinScope
import org.koin.compose.scope.KoinScope


@Composable

inline fun <reified T : ViewModel> koinViewModel(): T {

    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}