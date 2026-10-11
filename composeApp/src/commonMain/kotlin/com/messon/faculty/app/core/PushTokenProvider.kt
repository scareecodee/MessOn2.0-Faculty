package com.messon.faculty.app.core

import kotlinx.coroutines.flow.Flow


interface PushTokenProvider {
    suspend fun getToken(): String?
    fun observeTokenChanges(): Flow<String>
}