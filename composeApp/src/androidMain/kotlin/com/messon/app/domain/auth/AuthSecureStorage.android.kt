package com.messon.app.domain.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import co.touchlab.kermit.Logger
import com.messon.app.data.remote.dto.LoginResponseDto
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


actual class AuthSecureStorage(
    context: Context
) {
    val Log = Logger.withTag("CMP_LOG")

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual suspend fun saveLogin(response: LoginResponseDto) {
        Log.d(
            tag = "savedOnDisk", message = {response.toString()}
        )
        prefs.edit().apply {
            clear()
            putString("access_token", response.accessToken)
            putString("token_type", response.tokenType)
            putString("email", response.user.email)
            putString("name", response.user.name)
            putString("picture", response.user.picture)
            putString("expires_at", response.expiresIn)
            putString("id", response.user.id)
            apply()
        }
    }
    actual suspend fun getName(): String? =
        prefs.getString("name", null)

    actual suspend fun getPicture(): String? =
        prefs.getString("picture", null)

    actual suspend fun getId(): String? =
        prefs.getString("id", null)

    actual suspend fun getAccessToken(): String? =
        prefs.getString("access_token", null)

    actual suspend fun getEmail(): String? =
        prefs.getString("email", null)

    actual suspend fun getExpiresAt(): String? =
        prefs.getString("expires_at", null)


    actual suspend fun isSessionValid(): Boolean {
        val token = getAccessToken() ?: return false
        val expiresAt = getExpiresAt() ?: return false
        val normalized = expiresAt.substringBefore('.') + "Z"
        val expiryInstant = Instant.parse(normalized)
        val now = Clock.System.now()

        if(now < expiryInstant)
            return true
        else{
            clear()
            return false
        }
    }


    actual suspend fun clear() {
        prefs.edit().clear().apply()
    }
}
