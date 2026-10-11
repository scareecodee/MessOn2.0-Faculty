package com.messon.faculty.app.core
import com.messon.faculty.app.domain.dataModel.NotificationPayload
import com.messon.faculty.app.domain.dataModel.PermissionStatus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AndroidPushBridge : PushBridge {

    private val _notificationsFlow = MutableSharedFlow<NotificationPayload>(
        replay = 0, extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val notificationsFlow: SharedFlow<NotificationPayload> =
        _notificationsFlow.asSharedFlow()

    private val _tokenRefreshFlow = MutableSharedFlow<String>(
        replay = 1, extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val tokenRefreshFlow: SharedFlow<String> = _tokenRefreshFlow.asSharedFlow()

    private val _deepLinkFlow = MutableSharedFlow<String>(
        replay = 0, extraBufferCapacity = 8,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val deepLinkFlow: SharedFlow<String> = _deepLinkFlow.asSharedFlow()

    override var currentToken: String? = null
        private set

    override fun onNewToken(token: String) {
        currentToken = token
        _tokenRefreshFlow.tryEmit(token)
    }

    override fun onNotificationReceived(
        messageId: String, type: String, title: String, body: String,
        deepLink: String?, imageUrl: String?, metadata: Map<String, Any?>, sentAt: Long
    ) {
        _notificationsFlow.tryEmit(
            NotificationPayload(
                messageId = messageId, type = type, title = title, body = body,
                deepLink = deepLink, imageUrl = imageUrl,
                metadata = metadata.mapValues { it.value?.toString() ?: "" },
                sentAt = sentAt
            )
        )
    }

    override fun onDeepLinkTapped(deepLink: String) { _deepLinkFlow.tryEmit(deepLink) }

    override fun registerForRemoteNotifications() { /* FirebaseMessaging.getInstance().token */ }
    override fun setRegisterCallback(callback: () -> Unit) { /* no-op */ }
    override fun unregister() { currentToken = null }

    override suspend fun requestPermission(): PermissionStatus = PermissionStatus.GRANTED
    override fun requestPermissionWithCallback(callback: (PermissionStatus) -> Unit) {
        callback(PermissionStatus.GRANTED)
    }
    override fun onPermissionResult(granted: Boolean) { /* no-op */ }
}