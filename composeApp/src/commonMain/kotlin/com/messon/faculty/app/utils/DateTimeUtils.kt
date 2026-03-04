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