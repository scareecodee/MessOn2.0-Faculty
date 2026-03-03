package com.messon.faculty.app.domain.dataModel


data class Student(
    val name:String,
    val studentId: String,
    val batch:Int,
    val branch: String,
    val profileImg: String,
    val isActive: Boolean,
    val email: String
)


data class StudentInput(
    val name:String,
    val studentId: String,
    val batch:Int,
    val branch: String,
    val isActive: Boolean,
    val email: String
)


data class AddStudentsManuallyRes(
    val status:Boolean,
    val conflicts:List<Conflict>
)


data class Conflict(
    val email: String,
    val studentId: String,
    val message: String
)


data class ToggleStatusRes(
    val isActive: Boolean
)