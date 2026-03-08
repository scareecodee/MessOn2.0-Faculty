package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.ConfirmImportResponseDto
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes


import com.messon.faculty.app.data.remote.dto.PreviewImportResponseDto
import com.messon.faculty.app.domain.dataModel.ConfirmImportRes
import com.messon.faculty.app.domain.dataModel.ImportValidationError

fun PreviewImportResponseDto.toDomain(): CsvPreviewRes {
    return CsvPreviewRes(
        status=status,
        importToken = importToken,
        newRows = newRows,
        updateRows = updateRows,
        conflicts = conflicts.map {
            ImportValidationError(
                rowNumber = it.rowNumber,
                message = it.message
            )
        },
        expiresAt = expiresAt,
        errors = errors.map {
            ImportValidationError(
                rowNumber = it.rowNumber,
                message = it.message
            )
        }
    )
}

fun ConfirmImportResponseDto.toDomain(): ConfirmImportRes{
    return ConfirmImportRes(
        status = status
    )
}