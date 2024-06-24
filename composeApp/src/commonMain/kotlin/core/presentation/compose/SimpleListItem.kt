package core.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleListItem(
    modifier: Modifier = Modifier,
    showArrow: Boolean = true,
    leading: ImageVector,
    headline: String = "",
    trailing: String = "",
    supportText: String = "",
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.background
) {


    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = color,
        ),
        supportingContent = {

            if (supportText.isNotEmpty())
                Text(
                    text = supportText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                )
        },
        modifier = modifier
            .clickable {
                onClick()
            },
        leadingContent = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = leading,
                contentDescription = null,
            )
        },
        headlineContent = {
            Text(
                text = headline,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        trailingContent = {
            Text(
                trailing,
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}