package com.messon.student.app.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class DeviceRegistrationRequest(
    val token: String,
    val platform: String,
    val deviceId: String,
    val appVersion: String,
    val osVersion: String,
    val locale: String
)

@Serializable
data class DeviceRegistrationResponse(
    val id: String,
    val userId: String,
    val deviceId: String,
    val token: String,
    val platform: String,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class DeviceUpdateRequest(
    val token: String? = null,
    val appVersion: String? = null,
    val osVersion: String? = null,
    val locale: String? = null
)
