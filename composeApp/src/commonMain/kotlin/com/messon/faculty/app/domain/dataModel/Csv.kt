package com.messon.faculty.app.domain.dataModel

data class CsvPreviewRes(

val importToken: String,
val totalRows: Int,
val toInsert: Int,
val toUpdate: Int,
val errors: List<Map<String, String>>
)

