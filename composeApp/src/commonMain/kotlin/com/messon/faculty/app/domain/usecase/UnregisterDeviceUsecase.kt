package com.messon.faculty.app.domain.usecase

import com.messon.faculty.app.domain.repository.DeviceRepository
import com.messon.faculty.app.utils.AppResult

class UnregisterDeviceUseCase(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: String): AppResult<Unit> {
        return deviceRepository.unregisterDevice(deviceId)
    }
}