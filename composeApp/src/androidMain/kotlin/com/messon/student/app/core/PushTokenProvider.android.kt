package com.messon.student.app.core

import kotlinx.coroutines.flow.Flow


class AndroidPushTokenProvider(
    private val pushNotificationManager: PushNotificationManager
) : PushTokenProvider {

    override suspend fun getToken(): String? {
        return try {
            pushNotificationManager.awaitToken()
        } catch (e: Exception) {
            null
        }
    }

    override fun observeTokenChanges(): Flow<String> {
        return pushNotificationManager.observeTokenRefresh()
    }
}