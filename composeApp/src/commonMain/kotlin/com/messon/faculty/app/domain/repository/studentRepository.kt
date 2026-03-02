package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.dto.StudentDto
import com.messon.faculty.app.data.remote.network.Student
import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class StudentRepository(
    private val student: Student
) {

    suspend fun addStudentsManually(
//        token: String,
        students:List<StudentDto>
    ): Result<AddStudentsManuallyRes, NetworkError> {

        return when (val result = student.addStudentsManually(students = students)) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

}
