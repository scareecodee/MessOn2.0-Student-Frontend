package com.messon.student.app.core

import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.PermissionStatus
import kotlinx.coroutines.flow.Flow

actual class PushNotificationManager {
    actual fun registerForPushNotifications() {
        IosPushBridge.registerForRemoteNotifications()
    }

    actual fun unregisterFromPushNotifications() {
        IosPushBridge.unregister()
    }

    actual fun observeNotifications(): Flow<NotificationPayload> {
        return IosPushBridge.notificationsFlow
    }

    actual fun observeTokenRefresh(): Flow<String> {
        return IosPushBridge.tokenRefreshFlow
    }

    actual suspend fun requestPermission(): PermissionStatus {
        return IosPushBridge.requestPermission()
    }

    actual fun getCurrentToken(): String? {
        return IosPushBridge.currentToken
    }
}
