package core.presentation.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import core.presentation.designsystem.theme.Spacing


@Composable
fun <T> AppRadio(
    modifier: Modifier = Modifier,
    items: List<T> = listOf(),
    onChanged: (T) -> Unit = {},
    label: ((T?) -> String)? = null,
    labelContent: (@Composable (T?) -> Unit)? = null,
    title: String = "",
    titleStyle: TextStyle? = null,
    selected: (T) -> Boolean,
) {
    Column(modifier = modifier) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = titleStyle
                    ?: MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
            )
        }
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .padding(horizontal = Spacing.md)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                label?.let {
                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = it(item),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                labelContent?.let {
                    it(item)
                }
                RadioButton(
                    selected = selected(item),
                    onClick = { onChanged(item) },
                )
            }
        }
    }

}
