package features.weather.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.domain.model.weather.HourlyWeather
import core.presentation.compose.NetworkImage
import core.presentation.designsystem.theme.Spacing


@Composable
fun HourlyForecastComposeBuilder(
    modifier: Modifier = Modifier,
    items: List<HourlyWeather>
) {

    LazyRow(modifier = modifier.padding(end = Spacing.xs)) {
        items(items) { item ->
            HourlyForecastItem(daily = item)
        }
    }
}

@Composable
fun HourlyForecastItem(
    modifier: Modifier = Modifier,
    daily: HourlyWeather
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (daily.weather.isNotEmpty()) {
            NetworkImage(
                modifier = Modifier.size(26.dp).padding(
                    horizontal = Spacing.xs
                ),
                url = daily.weather.first().icon,
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                daily.forecastedTime,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                daily.temperature,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}