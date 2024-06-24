package core.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import kmp_weather.composeapp.generated.resources.Res
import kmp_weather.composeapp.generated.resources.conflict
import kmp_weather.composeapp.generated.resources.disk_full
import kmp_weather.composeapp.generated.resources.io_error
import kmp_weather.composeapp.generated.resources.no_network
import kmp_weather.composeapp.generated.resources.pay_load_large
import kmp_weather.composeapp.generated.resources.request_timeout
import kmp_weather.composeapp.generated.resources.serialization_error
import kmp_weather.composeapp.generated.resources.server_error
import kmp_weather.composeapp.generated.resources.too_many_requests
import kmp_weather.composeapp.generated.resources.un_auth
import kmp_weather.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


sealed class UiText {

    data object Empty : UiText()

    data class Dynamic(val value: String) : UiText()

    data class StringRes(val id: StringResource) : UiText()


    @Composable
    fun asString(): String {
        return when (this) {
            is Dynamic -> ""
            Empty -> ""
            is StringRes -> stringResource(this.id)
        }
    }


    @Composable
    fun AsText(
        modifier: Modifier = Modifier,
        style: TextStyle = TextStyle(),
    ) {
        val value = asString()
        Text(value, style = style, modifier = modifier)
    }

}


fun AppError.asUiText(): UiText {
    val id = when (this) {

        is CommonError -> {
            when (this) {
                CommonError.IO_ERROR -> Res.string.io_error
                CommonError.UNKNOWN -> Res.string.unknown_error
            }
        }

        is RemoteError -> {
            when (this) {
                RemoteError.REQUEST_TIMEOUT -> Res.string.request_timeout
                RemoteError.UNAUTHORIZED -> Res.string.un_auth
                RemoteError.CONFLICT -> Res.string.conflict
                RemoteError.TOO_MANY_REQUEST -> Res.string.too_many_requests
                RemoteError.NO_NETWORK -> Res.string.no_network
                RemoteError.PAYLOAD_TOO_LARGE -> Res.string.pay_load_large
                RemoteError.SERVER_ERROR -> Res.string.server_error
                RemoteError.SERIALIZATION_ERROR -> Res.string.serialization_error
                RemoteError.UNKNOWN_ERROR -> Res.string.unknown_error
            }
        }

        is LocalError -> {
            when (this) {
                LocalError.DISK_FULL -> Res.string.disk_full
                LocalError.UNKNOWN -> Res.string.unknown_error
            }
        }

        else -> Res.string.unknown_error
    }
    return UiText.StringRes(id)
}


