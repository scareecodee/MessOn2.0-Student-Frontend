package com.messon.student.app.core


import kotlinx.coroutines.flow.Flow

class IosPushTokenProvider(
    private val pushNotificationManager: PushNotificationManager
) : PushTokenProvider {

    override suspend fun getToken(): String? {
        return pushNotificationManager.awaitToken()
    }

    override fun observeTokenChanges(): Flow<String> {
        return pushNotificationManager.observeTokenRefresh()
    }
}