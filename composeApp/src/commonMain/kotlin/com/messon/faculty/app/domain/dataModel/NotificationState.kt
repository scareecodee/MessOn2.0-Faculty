package com.messon.faculty.app.domain.dataModel

import com.messon.faculty.app.core.AppError

sealed interface NotificationState {
    data object Idle : NotificationState
    data object Registering : NotificationState
    data class Registered(val token: String) : NotificationState
    data object Unregistering : NotificationState
    data object Unregistered : NotificationState
    data object PermissionDenied : NotificationState
    data class Error(val error: AppError) : NotificationState
}