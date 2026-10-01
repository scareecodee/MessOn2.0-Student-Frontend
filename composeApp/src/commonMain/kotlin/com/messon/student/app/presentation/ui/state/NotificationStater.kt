package com.messon.student.app.presentation.ui.state

import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.NotificationState
import com.messon.student.app.domain.dataModels.PermissionStatus

data class NotificationUiState(
    val notificationState: NotificationState = NotificationState.Idle,
    val permissionStatus: PermissionStatus = PermissionStatus.NOT_DETERMINED,
    val notifications: List<NotificationPayload> = emptyList(),
    val unreadCount: Int = 0
)