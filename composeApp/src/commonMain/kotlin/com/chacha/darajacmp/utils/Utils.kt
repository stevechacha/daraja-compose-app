package com.chacha.darajacmp.utils

import io.ktor.util.encodeBase64
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**Format current timestamp to YYYYMMDDHHmmss format*/
internal fun Instant.getDarajaTimestamp(): String {
    val currentDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())

    val year = currentDateTime.year
    val month = currentDateTime.monthNumber.asFormattedWithZero()
    val dayOfMonth = currentDateTime.dayOfMonth.asFormattedWithZero()
    val hour = currentDateTime.hour.asFormattedWithZero()
    val minutes = currentDateTime.minute.asFormattedWithZero()
    val seconds = currentDateTime.second.asFormattedWithZero()

    return "$year$month$dayOfMonth$hour$minutes$seconds"
}

internal fun Int.asFormattedWithZero(): String =
    when (this < 10) {
        true -> "0$this"
        false -> this.toString()
    }


/** Generates a base 64 string by encoding a combination of [shortCode], [passkey] and [timestamp]*/
internal fun getDarajaPassword(
    shortCode: String,
    passkey: String,
    timestamp: String,
): String {
    val password = shortCode + passkey + timestamp

    return password.encodeBase64()
}

internal fun String.getDarajaPhoneNumber(): String {
    val phoneNumber = this.replace("\\s".toRegex(), "")

    return when {
        phoneNumber.matches(Regex("^(?:254)?[17](?:\\d\\d|0[0-8]|(9[0-2]))\\d{6}\$")) -> phoneNumber
        phoneNumber.matches(Regex("^0?[17](?:\\d\\d|0[0-8]|(9[0-2]))\\d{6}\$")) ->
            phoneNumber.replaceFirst("0", "254")

        phoneNumber.matches(Regex("^(?:\\+254)?[17](?:\\d\\d|0[0-8]|(9[0-2]))\\d{6}\$")) ->
            phoneNumber.replaceFirst("+", "")

        else -> "Error "
    }
}

internal fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}