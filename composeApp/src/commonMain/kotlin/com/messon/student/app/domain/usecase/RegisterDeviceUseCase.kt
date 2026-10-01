package com.messon.student.app.domain.usecase

import com.messon.student.app.core.AppError
import com.messon.student.app.core.PushTokenProvider
import com.messon.student.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.domain.repository.DeviceRepository
import com.messon.student.app.utils.AppResult
import com.messon.student.app.utils.Result

class RegisterDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    private val tokenProvider: PushTokenProvider
) {
    suspend operator fun invoke(deviceInfo: DeviceInfo): AppResult<DeviceRegistrationResponse> {
        val token = tokenProvider.getToken()
        return if (token != null) {
            deviceRepository.registerDevice(token, deviceInfo)
        } else {
            Result.Error(AppError.Unknown("Failed to obtain push token"))    }
}}
