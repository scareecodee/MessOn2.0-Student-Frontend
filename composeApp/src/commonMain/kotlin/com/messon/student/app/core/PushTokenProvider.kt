package com.messon.student.app.core

import kotlinx.coroutines.flow.Flow

expect class PushTokenProvider() {
    suspend fun getToken(): String?
    fun observeTokenChanges(): Flow<String>
}