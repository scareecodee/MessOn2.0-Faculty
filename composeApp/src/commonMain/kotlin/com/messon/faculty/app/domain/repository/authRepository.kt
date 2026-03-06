package com.messon.faculty.app.domain.repository

import com.messon.faculty.app.data.remote.network.Auth
import com.messon.faculty.app.domain.dataModel.LoginRequest
import com.messon.faculty.app.domain.dataModel.LoginResponse
import com.messon.faculty.app.presentation.mapper.toDomain
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result


class AuthRepository (
    private val auth: Auth
){
    suspend fun login(
        loginRequest: LoginRequest
    ): Result<LoginResponse, NetworkError>{
        val dtoResult=auth.login(loginRequest.toDto())

        return when (dtoResult) {
            is Result.Success -> {
                val domain= dtoResult.data.toDomain()
                Result.Success(domain)
            }
            is Result.Error -> {
                Result.Error(dtoResult.error)
            }
        }
    }
}