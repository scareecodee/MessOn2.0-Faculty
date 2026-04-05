package com.messon.faculty.app.utils
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun LocalDateTime.toHumanReadable(): String {
    val months = listOf(
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    )

    val hour12 = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }

    val ampm = if (hour < 12) "AM" else "PM"

    val minuteFormatted = minute.toString().padStart(2, '0')

    return "$dayOfMonth ${months[monthNumber - 1]} $year, $hour12:$minuteFormatted $ampm"
}

fun formatDate(input: String): String {
    if(input.isEmpty()) return ""
    val date = LocalDate.parse(input)

    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year

    return "$day-$month-$year"
}


fun LocalDate.toReadableFormat(): String {
    val monthName = when (this.monthNumber) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }

    val dayFormatted = this.dayOfMonth.toString().padStart(2, '0')

    return "$dayFormatted $monthName, ${this.year}"
}

fun Long.toFormattedDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDate = instant
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        .date

    val month = localDate.month.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    return "${localDate.dayOfMonth} $month ${localDate.year}"
}