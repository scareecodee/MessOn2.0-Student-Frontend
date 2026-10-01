package com.messon.student.app.data.remote.network

import com.messon.student.app.data.remote.dto.DeviceRegistrationRequest
import com.messon.student.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.student.app.data.remote.dto.DeviceUpdateRequest
import com.messon.student.app.utils.AppResult

interface NotificationApi {
    suspend fun registerDevice(request: DeviceRegistrationRequest): AppResult<DeviceRegistrationResponse>
    suspend fun unregisterDevice(deviceId: String): AppResult<Unit>
    suspend fun updateDevice(deviceId: String, request: DeviceUpdateRequest): AppResult<DeviceRegistrationResponse>
}