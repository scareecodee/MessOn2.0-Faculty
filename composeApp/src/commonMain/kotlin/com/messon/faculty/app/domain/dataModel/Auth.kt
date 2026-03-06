package com.messon.faculty.app.domain.dataModel

data class  LoginResponse(
    val accessToken: String,
    val tokenType: String = "bearer",
    val user: User,
    val expiresIn: String? = null,
)


data class LoginRequest(
    val idToken: String
)