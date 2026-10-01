package com.messon.student.app.core

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await


actual class PushTokenProvider actual constructor() {
    actual suspend fun getToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            null
        }
    }

    actual fun observeTokenChanges(): Flow<String> {
        return PushNotificationManager.tokenRefreshFlow
    }
}