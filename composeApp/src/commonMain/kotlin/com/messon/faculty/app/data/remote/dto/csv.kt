package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CsvPreviewResponseDto(
    @SerialName("import_token")
    val importToken: String,

    @SerialName("total_rows")
    val totalRows: Int,

    @SerialName("to_insert")
    val toInsert: Int,

    @SerialName("to_update")
    val toUpdate: Int,

    @SerialName("errors")
    val errors: List<Map<String, String>>
)
