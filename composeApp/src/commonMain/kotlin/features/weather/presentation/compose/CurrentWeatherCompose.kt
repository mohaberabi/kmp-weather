package features.weather.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import core.domain.model.weather.CurrentWeather
import core.presentation.designsystem.theme.Spacing


@Composable
fun CurrentWeatherCompose(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Currently",
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.height(Spacing.sm))

        Text(
            currentWeather.temperature,
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Black),
        )
        Spacer(Modifier.height(Spacing.md))

        Text(
            "Feels Like ${currentWeather.feelsLike}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(Spacing.md))
    }


}