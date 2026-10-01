package com.messon.student.app.services
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannelManager {
    const val CHANNEL_GENERAL = "general"
    const val CHANNEL_TRANSACTIONAL = "transactional"
    const val CHANNEL_MARKETING = "marketing"
    const val CHANNEL_SYSTEM = "system"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val general = NotificationChannel(
                CHANNEL_GENERAL,
                "General",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val transactional = NotificationChannel(
                CHANNEL_TRANSACTIONAL,
                "Transactional",
                NotificationManager.IMPORTANCE_HIGH
            )
            val marketing = NotificationChannel(
                CHANNEL_MARKETING,
                "Marketing",
                NotificationManager.IMPORTANCE_LOW
            )
            val system = NotificationChannel(
                CHANNEL_SYSTEM,
                "System",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannels(listOf(general, transactional, marketing, system))
        }
    }
}
