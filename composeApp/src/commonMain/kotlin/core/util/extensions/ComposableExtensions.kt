package core.util.extensions

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable


fun LazyGridScope.spannedItem(
    cols: Int,
    key: Any? = null,
    contentType: Any? = null,
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(
        key = key,
        contentType = contentType,
        span = {
            GridItemSpan(cols)
        }
    ) {
        content()
    }
}
