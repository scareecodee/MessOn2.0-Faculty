package com.messon.faculty.app.core

import com.messon.faculty.app.domain.dataModel.NotificationPayload
import com.messon.faculty.app.domain.dataModel.PermissionStatus
import kotlinx.coroutines.flow.Flow

expect class PushNotificationManager() {
    fun registerForPushNotifications()
    fun unregisterFromPushNotifications()
    fun observeNotifications(): Flow<NotificationPayload>
    fun observeTokenRefresh(): Flow<String>
    suspend fun requestPermission(): PermissionStatus
    suspend fun getCurrentToken(): String?
    suspend fun awaitToken(timeoutMs: Long = 10_000): String?

}