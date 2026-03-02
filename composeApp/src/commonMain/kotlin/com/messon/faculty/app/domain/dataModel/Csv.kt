package com.messon.faculty.app.domain.dataModel


data class CsvPreviewRes(

    val status: Boolean,

    val importToken: String?,

    val newRows: Int?,

    val updateRows: Int?,

    val conflicts: List<String>,

    val expiresAt: String?,

    val errors: List<ImportValidationError>
)

data class ImportValidationError(
    val rowNumber: Int,
    val message: String
)