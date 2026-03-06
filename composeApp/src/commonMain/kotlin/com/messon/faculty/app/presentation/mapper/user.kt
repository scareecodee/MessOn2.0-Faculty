package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.UserDto
import com.messon.faculty.app.domain.dataModel.User

fun UserDto.toDomain(): User{
    return User(
        name = name,
        mailId = email,
        profileImg = picture?:"",
        id = id,
    )
}

fun User.toDto(id:String): UserDto{
    return UserDto(
        name = name,
        email = mailId,
        picture = profileImg,
        id = id
    )
}