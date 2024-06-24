package core.util.extensions


import custom.util.formatLocalDateTime
import kotlinx.datetime.Instant

import kotlinx.datetime.TimeZone

import kotlinx.datetime.toLocalDateTime


fun Long.formatTimeMillis(format: String): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return formatLocalDateTime(localDateTime, format)
}