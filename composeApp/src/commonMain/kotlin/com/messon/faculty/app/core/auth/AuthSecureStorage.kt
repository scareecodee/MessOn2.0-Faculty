package com.messon.faculty.app.core.auth

import com.messon.faculty.app.domain.dataModel.LoginResponse

expect class AuthSecureStorage{
    suspend fun saveLogin(response: LoginResponse)
    suspend fun getAccessToken(): String?
    suspend fun getEmail(): String?
    suspend fun getPicture():String?
    suspend fun getName():String?

    suspend fun getId():String?

    suspend fun getExpiresAt(): String?

    suspend fun isSessionValid(): Boolean

    suspend fun clear()
}