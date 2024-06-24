package custom.util

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
actual fun formatLocalDateTime(
    localDateTime: LocalDateTime, format: String,
): String {
    val formatted = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        .format(localDateTime.toJavaLocalDateTime())
    return formatted
}