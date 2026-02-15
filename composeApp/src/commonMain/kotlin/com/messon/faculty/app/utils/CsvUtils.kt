package com.messon.faculty.app.utils

fun parseCsv(content: String): List<List<String>> {
    return content
        .trim()
        .lines()
        .map { line ->
            line.split(",")
        }
}
