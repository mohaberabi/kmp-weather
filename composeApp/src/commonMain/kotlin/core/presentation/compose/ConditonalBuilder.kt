package core.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ConditionalBuilder(
    modifier: Modifier = Modifier,
    onPositive: @Composable () -> Unit,
    onNegative: @Composable () -> Unit,
    condition: Boolean,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        if (condition) {
            onPositive()
        } else {
            onNegative()
        }
    }

}