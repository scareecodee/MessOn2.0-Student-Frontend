package com.messon.app.domain.auth


import com.messon.app.data.remote.dto.LoginResponseDto

expect class AuthSecureStorage{
    suspend fun saveLogin(response: LoginResponseDto)
    suspend fun getAccessToken(): String?
    suspend fun getEmail(): String?
    suspend fun getPicture():String?
    suspend fun getName():String?

    suspend fun getId():String?

    suspend fun getExpiresAt(): String?

     suspend fun isSessionValid(): Boolean

    suspend fun clear()
}
