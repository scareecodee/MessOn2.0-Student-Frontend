package com.messon.student.app.domain.repository
import com.messon.student.app.data.remote.dto.DeviceRegistrationRequest
import com.messon.student.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.student.app.data.remote.dto.DeviceUpdateRequest
import com.messon.student.app.data.remote.network.NotificationApi
import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.utils.AppResult
import com.messon.student.app.utils.map

class DeviceRepositoryImpl(
    private val notificationApi: NotificationApi
) : DeviceRepository {

    override suspend fun registerDevice(
        token: String,
        deviceInfo: DeviceInfo
    ): AppResult<DeviceRegistrationResponse> {
        val request = DeviceRegistrationRequest(
            token = token,
            platform = deviceInfo.platform.name,
            deviceId = deviceInfo.deviceId,
            appVersion = deviceInfo.appVersion,
            osVersion = deviceInfo.osVersion,
            locale = deviceInfo.locale
        )
        return notificationApi.registerDevice(request)
    }

    override suspend fun unregisterDevice(deviceId: String): AppResult<Unit> {
        return notificationApi.unregisterDevice(deviceId)
    }

    override suspend fun updateDeviceToken(
        deviceId: String,
        newToken: String
    ): AppResult<DeviceRegistrationResponse> {
        val request = DeviceUpdateRequest(token = newToken)
        return notificationApi.updateDevice(deviceId, request)
    }
}