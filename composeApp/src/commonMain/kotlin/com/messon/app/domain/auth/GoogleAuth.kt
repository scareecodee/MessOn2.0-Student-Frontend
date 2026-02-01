package com.messon.app.domain.auth

import com.messon.app.utils.NetworkError
import com.messon.app.utils.Result

interface GoogleAuth {
    suspend fun getGoogleToken(): Result<String, NetworkError>
}