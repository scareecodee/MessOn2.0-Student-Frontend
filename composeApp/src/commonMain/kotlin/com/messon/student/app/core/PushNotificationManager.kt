package com.messon.student.app.core

import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.PermissionStatus
import kotlinx.coroutines.flow.Flow

expect class PushNotificationManager() {
    fun registerForPushNotifications()
    fun unregisterFromPushNotifications()
    fun observeNotifications(): Flow<NotificationPayload>
    fun observeTokenRefresh(): Flow<String>
    suspend fun requestPermission(): PermissionStatus
    fun getCurrentToken(): String?
}