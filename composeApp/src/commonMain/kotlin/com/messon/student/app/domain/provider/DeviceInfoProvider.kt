package com.messon.student.app.domain.provider

import com.messon.student.app.domain.dataModels.DeviceInfo

interface DeviceInfoProvider {
    suspend fun getDeviceInfo(): DeviceInfo
}