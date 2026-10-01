package com.messon.student.app.domain.repository

import com.messon.student.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.utils.AppResult

interface DeviceRepository {
    suspend fun registerDevice(token: String, deviceInfo: DeviceInfo): AppResult<DeviceRegistrationResponse>
    suspend fun unregisterDevice(deviceId: String): AppResult<Unit>
    suspend fun updateDeviceToken(deviceId: String, newToken: String): AppResult<DeviceRegistrationResponse>
}
