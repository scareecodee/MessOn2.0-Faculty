package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class PreviewImportResponseDto(

    @SerialName("isActive")
    val isActive: Boolean,

    @SerialName("import_token")
    val importToken: String? = null,

    @SerialName("new_rows")
    val newRows: Int? = null,

    @SerialName("update_rows")
    val updateRows: Int? = null,

    @SerialName("conflicts")
    val conflicts: List<ImportValidationErrorDto> = emptyList(),

    @SerialName("expires_at")
    val expiresAt: String? = null,

    @SerialName("errors")
    val errors: List<ImportValidationErrorDto> = emptyList()
)

@Serializable
data class ImportValidationErrorDto(

    @SerialName("row_number")
    val rowNumber: Int,

    @SerialName("message")
    val message: String
)

@Serializable
data class ConfirmImportResponseDto(
    val isActive: Boolean
)