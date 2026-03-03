package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.AddStudentsManuallyResDto
import com.messon.faculty.app.data.remote.dto.StudentDto
import com.messon.faculty.app.data.remote.dto.ToggleStatusResDto
import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
import com.messon.faculty.app.domain.dataModel.Conflict
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.domain.dataModel.ToggleStatusRes

fun StudentInput.toDto(): StudentDto {
    return StudentDto(
        name = name,
        studentId = studentId,
        batch = batch,
        branch = branch,
        profileImg = null,
        isActive = isActive,
        email = email
    )
}
fun AddStudentsManuallyResDto.toDomain(): AddStudentsManuallyRes{
    return AddStudentsManuallyRes(
        status=status,
        conflicts =conflicts.map {
            Conflict(
                email =it.email,
                studentId = it.studentId,
                message = it.message
            )
        }
    )
}

fun ToggleStatusResDto.toDomain(): ToggleStatusRes{
    return ToggleStatusRes(
        isActive=isActive
    )
}