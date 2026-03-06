package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.CollegeDto
import com.messon.faculty.app.domain.dataModel.College

fun CollegeDto.toDomain(): College{
    return College(
        collegeId = collegeId,
        collegeName =collegeName
    )
}