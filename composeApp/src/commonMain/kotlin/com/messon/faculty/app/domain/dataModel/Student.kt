package com.messon.faculty.app.domain.dataModel

data class Student(
    val name:String,
    val studentId: String,
    val year:Int,
    val branch: String,
    val profileImg: String,
    val isActive: Boolean
)