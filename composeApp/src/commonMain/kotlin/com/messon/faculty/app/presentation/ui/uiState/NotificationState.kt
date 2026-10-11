package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.NotificationPayload
import com.messon.faculty.app.domain.dataModel.NotificationState
import com.messon.faculty.app.domain.dataModel.PermissionStatus

data class NotificationUiState(
    val notificationState: NotificationState = NotificationState.Idle,
    val permissionStatus: PermissionStatus = PermissionStatus.NOT_DETERMINED,
    val notifications: List<NotificationPayload> = emptyList(),
    val unreadCount: Int = 0
)