package com.messon.faculty.app.domain.usecase

import com.messon.faculty.app.core.AppError
import com.messon.faculty.app.core.PushTokenProvider
import com.messon.faculty.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.faculty.app.domain.dataModel.DeviceInfo
import com.messon.faculty.app.domain.repository.DeviceRepository
import com.messon.faculty.app.utils.AppResult
import com.messon.faculty.app.utils.Result

class RegisterDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    private val tokenProvider: PushTokenProvider
) {
    suspend operator fun invoke(deviceInfo: DeviceInfo): AppResult<DeviceRegistrationResponse> {
        val token = tokenProvider.getToken()
            ?: return Result.Error(AppError.Unknown("Failed to obtain push token"))
        return deviceRepository.registerDevice(token, deviceInfo)
    }
}