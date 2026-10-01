package com.messon.student.app.domain.dataModels

import com.messon.student.app.core.AppError


sealed interface NotificationState {
    data object Idle : NotificationState
    data object Registering : NotificationState
    data class Registered(val token: String) : NotificationState
    data object Unregistering : NotificationState
    data object Unregistered : NotificationState
    data class Error(val error: AppError) : NotificationState
}
