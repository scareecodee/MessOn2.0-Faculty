package com.messon.faculty.app.domain.dataModel

data class AdminCreateReq(
    val name: String,
    val mailId: String,
    val isActive: Boolean
)


data class AdminCreateRes(

    val id: String,
    val name: String,
    val mailId: String,
    val isActive: Boolean

)