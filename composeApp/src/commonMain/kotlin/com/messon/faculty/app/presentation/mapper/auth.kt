package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.LoginRequestDto
import com.messon.faculty.app.data.remote.dto.LoginResponseDto
import com.messon.faculty.app.domain.dataModel.LoginRequest
import com.messon.faculty.app.domain.dataModel.LoginResponse

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        user = user.toDomain(),
        accessToken =accessToken,
        expiresIn = expiresIn,
        tokenType = tokenType
    )
}

fun LoginRequest.toDto(): LoginRequestDto{
    return LoginRequestDto(
        idToken = idToken,
        role = role,
        collegeId = collegeId
    )
}