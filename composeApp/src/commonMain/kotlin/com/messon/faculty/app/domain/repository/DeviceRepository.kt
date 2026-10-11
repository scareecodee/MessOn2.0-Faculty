package com.messon.faculty.app.domain.repository

import com.messon.faculty.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.faculty.app.domain.dataModel.DeviceInfo
import com.messon.faculty.app.utils.AppResult

interface DeviceRepository {
    suspend fun registerDevice(token: String, deviceInfo: DeviceInfo): AppResult<DeviceRegistrationResponse>
    suspend fun unregisterDevice(deviceId: String): AppResult<Unit>
    suspend fun updateDeviceToken(deviceId: String, newToken: String): AppResult<DeviceRegistrationResponse>
}