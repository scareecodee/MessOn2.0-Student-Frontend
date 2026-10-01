package com.messon.student.app.core
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedTokenStorage(context: Context) {
    companion object {
        const val KEY_FCM_TOKEN = "fcm_token"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "push_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_FCM_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_FCM_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit().remove(KEY_FCM_TOKEN).apply()
    }
}
