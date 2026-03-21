package com.messon.faculty.app.utils

//
//import kotlinx.datetime.TimeZone
//import kotlinx.datetime.toLocalDateTime
//
///**
// * Gets the current year safely in KMP.
// * Uses the fully qualified name for Clock to avoid Standard Library conflicts.
// */
//fun getCurrentYear(): Int {
//    // We call the kotlinx version explicitly to get a kotlinx.datetime.Instant
//    val now = kotlinx.datetime.Clock.System.now()
//
//    // Now .toLocalDateTime() will work perfectly because 'now' is the correct type
//    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
//
//    return localDateTime.year
//}


import kotlinx.datetime.LocalDateTime

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