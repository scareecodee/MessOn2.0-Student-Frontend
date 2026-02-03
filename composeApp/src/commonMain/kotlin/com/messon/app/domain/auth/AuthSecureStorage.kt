package com.messon.app.domain.auth

import com.messon.app.domain.dataModels.LoginResponse


expect class AuthSecureStorage{
    suspend fun saveLogin(response: LoginResponse)
    suspend fun getAccessToken(): String?
    suspend fun getEmail(): String?
    suspend fun getPicture():String?
    suspend fun getName():String?

    suspend fun getId():String?

    suspend fun getExpiresAt(): String?

     suspend fun isSessionValid(): Boolean

    suspend fun clear()
}
