package com.messon.faculty.app.utils

fun parseCsv(content: String): List<List<String>> {
    return content
        .trim()
        .lines()
        .map { line ->
            line.split(",")
        }
}


enum class CsvUploadMode(val value: String) {
    UPDATE("update"),
    INSERT("insert")
}