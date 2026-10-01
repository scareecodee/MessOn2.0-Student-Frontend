package com.messon.student.app.core

import kotlinx.coroutines.flow.Flow

actual class PushTokenProvider {
    actual suspend fun getToken(): String? {
        return IosPushBridge.currentToken
    }

    actual fun observeTokenChanges(): Flow<String> {
        return IosPushBridge.tokenRefreshFlow
    }
}
