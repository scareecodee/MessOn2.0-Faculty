package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DeleteUsersDto(
    @SerialName("role")
    val role: String,

    @SerialName("user_id")
    val userIds: List<String>
)