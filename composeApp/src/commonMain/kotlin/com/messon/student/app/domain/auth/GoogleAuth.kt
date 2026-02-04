package com.messon.student.app.domain.auth

import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result

interface GoogleAuth {
    suspend fun getGoogleToken(): Result<String, NetworkError>
}