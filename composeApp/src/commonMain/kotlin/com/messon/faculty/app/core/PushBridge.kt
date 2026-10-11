package com.messon.faculty.app.core
import com.messon.faculty.app.domain.dataModel.NotificationPayload
import com.messon.faculty.app.domain.dataModel.PermissionStatus
import kotlinx.coroutines.flow.SharedFlow

interface PushBridge {
    val notificationsFlow: SharedFlow<NotificationPayload>
    val tokenRefreshFlow: SharedFlow<String>
    val deepLinkFlow: SharedFlow<String>
    val currentToken: String?

    fun onNewToken(token: String)

    fun onNotificationReceived(
        messageId: String,
        type: String,
        title: String,
        body: String,
        deepLink: String?,
        imageUrl: String?,
        metadata: Map<String, Any?>,
        sentAt: Long
    )

    fun onDeepLinkTapped(deepLink: String)

    fun registerForRemoteNotifications()
    fun setRegisterCallback(callback: () -> Unit)
    fun unregister()

    suspend fun requestPermission(): PermissionStatus
    fun requestPermissionWithCallback(callback: (PermissionStatus) -> Unit)
    fun onPermissionResult(granted: Boolean)
}