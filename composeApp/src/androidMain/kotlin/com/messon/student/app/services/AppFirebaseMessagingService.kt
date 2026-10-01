package com.messon.student.app.services

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.messon.student.app.core.EncryptedTokenStorage
import com.messon.student.app.core.PushNotificationManager
import com.messon.student.app.domain.dataModels.NotificationPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.Collections

class AppFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val MAX_DEDUP_SIZE = 1000
        private val processedMessageIds: MutableSet<String> = Collections.synchronizedSet(
            Collections.newSetFromMap(
                object : LinkedHashMap<String, Boolean>(MAX_DEDUP_SIZE, 0.75f, true) {
                    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Boolean>?): Boolean =
                        size > MAX_DEDUP_SIZE
                }
            )
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val storage = EncryptedTokenStorage(applicationContext)
        storage.saveToken(token)
        runBlocking(Dispatchers.Default) {
            PushNotificationManager.tokenRefreshFlow.emit(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val messageId = data["messageId"] ?: message.messageId ?: return

        if (processedMessageIds.contains(messageId)) {
            return
        }
        processedMessageIds.add(messageId)

        val metadataString = data["metadata"] ?: "{}"
        val metadataMap = mutableMapOf<String, String>()
        try {
            val jsonObject = JSONObject(metadataString)
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                metadataMap[key] = jsonObject.getString(key)
            }
        } catch (e: Exception) {
        }

        val payload = NotificationPayload(
            messageId = messageId,
            type = data["type"] ?: "general",
            title = data["title"] ?: message.notification?.title ?: "",
            body = data["body"] ?: message.notification?.body ?: "",
            deepLink = data["deepLink"],
            imageUrl = data["imageUrl"],
            metadata = metadataMap,
            sentAt = data["sentAt"]?.toLongOrNull() ?: System.currentTimeMillis()
        )

        val tryEmitSuccess = PushNotificationManager.notificationsFlow.tryEmit(payload)
        if (!tryEmitSuccess) {
            runBlocking(Dispatchers.Default) {
                PushNotificationManager.notificationsFlow.emit(payload)
            }
        }

        showNotification(payload)
    }

    private fun showNotification(payload: NotificationPayload) {
        val channelId = when (payload.type) {
            "transactional" -> NotificationChannelManager.CHANNEL_TRANSACTIONAL
            "marketing" -> NotificationChannelManager.CHANNEL_MARKETING
            "system" -> NotificationChannelManager.CHANNEL_SYSTEM
            else -> NotificationChannelManager.CHANNEL_GENERAL
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            payload.deepLink?.let { data = Uri.parse(it) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            payload.messageId.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(payload.title)
            .setContentText(payload.body)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        try {
            notificationManager.notify(payload.messageId.hashCode(), builder.build())
        } catch (e: SecurityException) {
            // Permission denied, handle gracefully
        }
    }
}
