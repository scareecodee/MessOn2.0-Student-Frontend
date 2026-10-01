package com.messon.student.app.domain.dataModels
import kotlinx.serialization.Serializable

@Serializable
data class DeviceInfo(
    val deviceId: String,
    val platform: Platform,
    val appVersion: String,
    val osVersion: String,
    val locale: String
)

@Serializable
enum class Platform {
    ANDROID, IOS
}
