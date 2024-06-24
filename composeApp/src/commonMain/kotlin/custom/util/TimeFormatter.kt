package custom.util

import kotlinx.datetime.LocalDateTime


expect fun formatLocalDateTime(
    localDateTime: LocalDateTime, format: String,
): String