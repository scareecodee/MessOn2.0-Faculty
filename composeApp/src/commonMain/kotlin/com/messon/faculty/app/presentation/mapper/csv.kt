package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.CsvPreviewResponseDto
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes

fun CsvPreviewResponseDto.toDomain(): CsvPreviewRes{
    return CsvPreviewRes(
        importToken =importToken,
        totalRows =totalRows,
        toInsert = toInsert,
        toUpdate = toUpdate,
        errors = errors
    )
}