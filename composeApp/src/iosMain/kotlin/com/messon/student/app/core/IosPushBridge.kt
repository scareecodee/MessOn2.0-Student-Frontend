package com.messon.student.app.core

import com.messon.student.app.domain.dataModels.NotificationPayload
import com.messon.student.app.domain.dataModels.PermissionStatus
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object IosPushBridge {
    private val _notificationsFlow = MutableSharedFlow<NotificationPayload>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val notificationsFlow: SharedFlow<NotificationPayload> = _notificationsFlow.asSharedFlow()

    private val _tokenRefreshFlow = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val tokenRefreshFlow: SharedFlow<String> = _tokenRefreshFlow.asSharedFlow()

    private val _deepLinkFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 8,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val deepLinkFlow: SharedFlow<String> = _deepLinkFlow.asSharedFlow()

    var currentToken: String? = null
        private set

    private val processedMessageIds = mutableSetOf<String>()
    private const val MAX_DEDUP_SIZE = 1000

    fun onNewToken(token: String) {
        currentToken = token
        _tokenRefreshFlow.tryEmit(token)
    }

    fun onNotificationReceived(
        messageId: String,
        type: String,
        title: String,
        body: String,
        deepLink: String?,
        imageUrl: String?,
        metadata: Map<String, Any?>,
        sentAt: Long
    ) {
        if (isDuplicate(messageId)) return
        val stringMetadata = metadata.mapValues { it.value?.toString() ?: "" }
        val payload = NotificationPayload(
            messageId = messageId,
            type = type,
            title = title,
            body = body,
            deepLink = deepLink,
            imageUrl = imageUrl,
            metadata = stringMetadata,
            sentAt = sentAt
        )
        _notificationsFlow.tryEmit(payload)
    }

    fun onDeepLinkTapped(deepLink: String) {
        _deepLinkFlow.tryEmit(deepLink)
    }

    fun registerForRemoteNotifications() {
        // Actual registration happens in Swift AppDelegate
    }

    fun unregister() {
        currentToken = null
        // Actual Firebase token deletion happens in Swift
    }

    suspend fun requestPermission(): PermissionStatus = suspendCancellableCoroutine { continuation ->
        requestPermissionWithCallback { status ->
            continuation.resume(status)
        }
    }

    private var permissionCallback: ((PermissionStatus) -> Unit)? = null

    fun requestPermissionWithCallback(callback: (PermissionStatus) -> Unit) {
        permissionCallback = callback
    }

    fun onPermissionResult(granted: Boolean) {
        val status = if (granted) PermissionStatus.GRANTED else PermissionStatus.DENIED
        permissionCallback?.invoke(status)
        permissionCallback = null
    }

    private fun isDuplicate(messageId: String): Boolean {
        if (processedMessageIds.contains(messageId)) return true
        if (processedMessageIds.size >= MAX_DEDUP_SIZE) {
            processedMessageIds.clear()
        }
        processedMessageIds.add(messageId)
        return false
    }
}
