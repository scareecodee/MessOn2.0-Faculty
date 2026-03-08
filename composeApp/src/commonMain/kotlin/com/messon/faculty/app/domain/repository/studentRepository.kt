package com.messon.faculty.app.domain.repository
import com.messon.faculty.app.data.remote.dto.StudentDto
import com.messon.faculty.app.data.remote.dto.StudentInputDto
import com.messon.faculty.app.data.remote.network.StudentApi
import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
import com.messon.faculty.app.domain.dataModel.EditStudentRes
import com.messon.faculty.app.domain.dataModel.FetchStudentsRes
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.domain.dataModel.ToggleStatusRes
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

class StudentRepository(
    private val studentApi: StudentApi
) {

    suspend fun addStudentsManually(
        token: String,
        students:List<StudentInput>,
        messId: String
    ): Result<AddStudentsManuallyRes, NetworkError> {

        val studentsDto = students.map {student->
            StudentInputDto(
                name = student.name,
                studentId = student.studentId,
                email = student.email,
                branch = student.branch,
                batch = student.batch,
                isActive = student.isActive,
            )
        }
        return when (val result = studentApi.addStudentsManually(
            students = studentsDto,
            token = token,
            messId = messId
        )) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun toggleStatus(
//        token: String,
        uuid: String
    ): Result<ToggleStatusRes, NetworkError> {

        return when (val result = studentApi.toggleStudentStatus(uuid=uuid)) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun fetchStudents(
        token: String,
        search: String?,
        cursor: String?,
        messId: String

    ): Result<FetchStudentsRes, NetworkError> {

        return when (val result = studentApi.fetchStudents(
            cursor = cursor, search = search,
            token = token,
            messId = messId
        )) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data =dto.toDomain()
                )
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun editStudent(
//        token: String,
        uuid: String,
        updatedStudent:Student

        ): Result<EditStudentRes, NetworkError> {


        val updatedStudentDto=updatedStudent.toDto()

        return when (val result = studentApi.editStudent(updatedStudent=updatedStudentDto,uuid=uuid)) {

            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data =dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

}
