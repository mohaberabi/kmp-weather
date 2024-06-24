package features.weather.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.domain.model.weather.DailyWeather
import core.presentation.compose.NetworkImage
import core.presentation.designsystem.theme.Spacing


@Composable
fun DailyForecastBuilder(
    modifier: Modifier = Modifier,
    items: List<DailyWeather>
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            "This Week",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(Modifier.height(Spacing.sm))
        items.forEach { daily ->
            DailyForecastItem(
                daily = daily,
            )
        }
    }
}


@Composable
fun DailyForecastItem(
    modifier: Modifier = Modifier,
    daily: DailyWeather
) {


    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        if (daily.weather.isNotEmpty()) {
            NetworkImage(
                url = daily.weather.first().icon,
                modifier = Modifier.size(26.dp)
            )
        }
        Text(
            daily.forecastedTime,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )

        Row {
            if (daily.weather.isNotEmpty()) {
                NetworkImage(
                    url = daily.weather.first().icon,
                    modifier = Modifier.size(26.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    "Max ${daily.temperature.max}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    "Min ${daily.temperature.min}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

    }
}
