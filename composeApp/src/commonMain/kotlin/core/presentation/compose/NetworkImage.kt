package core.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

import coil3.compose.AsyncImage


@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}