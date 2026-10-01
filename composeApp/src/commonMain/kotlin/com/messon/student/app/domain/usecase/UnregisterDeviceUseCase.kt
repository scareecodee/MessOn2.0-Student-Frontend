package com.messon.student.app.domain.usecase

import com.messon.student.app.domain.repository.DeviceRepository
import com.messon.student.app.utils.AppResult


class UnregisterDeviceUseCase(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: String): AppResult<Unit> {
        return deviceRepository.unregisterDevice(deviceId)
    }
}
