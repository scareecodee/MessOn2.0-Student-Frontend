package com.messon.student.app.core

import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.PermissionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

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

    actual suspend fun getCurrentToken(): String? {
        IosPushBridge.currentToken?.let { return it }
        return withTimeoutOrNull(5000.milliseconds) {
            IosPushBridge.tokenRefreshFlow.first()
        }
    }

    actual suspend fun awaitToken(timeoutMs: Long): String? {
        IosPushBridge.currentToken?.let { return it }
        return withTimeoutOrNull(timeoutMs.milliseconds) {
            IosPushBridge.tokenRefreshFlow.first()
        }
    }
}
