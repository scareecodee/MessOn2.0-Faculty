package com.messon.faculty.app.domain.dataModel



data class Student(
    val name:String,
    val studentId: String,
    val batch:Int,
    val branch: String,
    val profileImg: String,
    val isActive: Boolean,
    val email: String,
    val uuid: String
)


data class StudentInput(
    val name:String,
    val studentId: String,
    val batch:Int,
    val branch: String,
    val isActive: Boolean,
    val email: String
)

data class FetchStudentsRes(
    val students:List<Student>,
    val cursor: String?
)


data class AddStudentsManuallyRes(
    val status:Boolean,
    val conflicts:List<Conflict>
)

data class EditStudentRes(
    val status: Boolean,
    val updatedStudent: Student?,
    val conflicts: Conflict?
)


data class Conflict(
    val email: String,
    val studentId: String,
    val message: String
)


data class ToggleStatusRes(
    val isActive: Boolean
)