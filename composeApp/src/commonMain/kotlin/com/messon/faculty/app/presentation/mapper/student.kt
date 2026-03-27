package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.AddStudentsManuallyResDto
import com.messon.faculty.app.data.remote.dto.ConflictDto
import com.messon.faculty.app.data.remote.dto.EditStudentResDto
import com.messon.faculty.app.data.remote.dto.FetchStudentsResDto
import com.messon.faculty.app.data.remote.dto.StudentDto
import com.messon.faculty.app.data.remote.dto.ToggleStatusResDto
import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
import com.messon.faculty.app.domain.dataModel.Conflict
import com.messon.faculty.app.domain.dataModel.EditStudentRes
import com.messon.faculty.app.domain.dataModel.FetchStudentsRes
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.domain.dataModel.ToggleStatusRes


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


fun StudentDto.toDomain(): Student{
    return Student(
        name =name,
        studentId = studentId,
        batch = batch,
        branch =branch,
        profileImg = profileImg?:"",
        isActive = isActive,
        email = email,
        uuid=uuid
    )
}


fun Student.toDto(): StudentDto{
    return StudentDto(
        name =name,
        studentId = studentId,
        batch = batch,
        branch =branch,
        profileImg = profileImg?:"",
        isActive = isActive,
        email = email,
        uuid = uuid
    )
}

fun FetchStudentsResDto.toDomain(): FetchStudentsRes{
    return FetchStudentsRes(
        students=students.map {
            it.toDomain()
        },
        cursor=nextCursor
    )
}


fun EditStudentResDto.toDomain(): EditStudentRes {
    return EditStudentRes(
        status=status,
        updatedStudent = updatedStudent?.toDomain(),
        conflicts = conflicts?.toDomain(),
    )
}


fun ConflictDto.toDomain(): Conflict{
    return Conflict(
        email =email,
        studentId = studentId,
        message = message
    )
}
