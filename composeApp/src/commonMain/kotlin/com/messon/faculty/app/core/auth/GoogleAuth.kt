package com.messon.faculty.app.core.auth

import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result

interface GoogleAuth {
    suspend fun getGoogleToken(): Result<String, NetworkError>
}