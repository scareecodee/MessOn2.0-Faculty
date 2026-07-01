package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerCreateReqDto(

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val mailId: String,


    @SerialName("is_active")
    val isActive: Boolean

)


@Serializable
data class ManagerCreateResDto(
//    @SerialName("id")
//    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val mailId: String,

    @SerialName("is_active")
    val isActive: Boolean

)