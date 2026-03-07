package com.messon.faculty.app.domain.dataModel



data class ManagerCreateReq(
    val name: String,
    val mailId: String,
    val isActive: Boolean
)


data class ManagerCreateRes(

    val id: String,
    val name: String,
    val mailId: String,
    val isActive: Boolean

)