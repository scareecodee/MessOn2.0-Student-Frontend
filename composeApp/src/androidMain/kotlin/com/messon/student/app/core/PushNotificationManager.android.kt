package com.messon.student.app.core
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.PermissionStatus
import com.messon.student.app.services.NotificationChannelManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

actual class PushNotificationManager actual constructor() {

    private val context = AndroidContextHolder.appContext
    private val tokenStorage = EncryptedTokenStorage(context)

    companion object {
        val notificationsFlow = MutableSharedFlow<NotificationPayload>(extraBufferCapacity = 64)
        val tokenRefreshFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    }

    actual fun registerForPushNotifications() {
        NotificationChannelManager.createChannels(context)
    }

    actual fun unregisterFromPushNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FirebaseMessaging.getInstance().deleteToken().await()
                tokenStorage.clearToken()
            } catch (_: Exception) { }
        }
    }

    actual fun observeNotifications(): Flow<NotificationPayload> =
        notificationsFlow.asSharedFlow()

    actual fun observeTokenRefresh(): Flow<String> =
        tokenRefreshFlow.asSharedFlow()

    actual suspend fun requestPermission(): PermissionStatus {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val status = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            )
            return if (status == PackageManager.PERMISSION_GRANTED)
                PermissionStatus.GRANTED else PermissionStatus.DENIED
        }
        return PermissionStatus.GRANTED
    }

    actual fun getCurrentToken(): String? = tokenStorage.getToken()
}
