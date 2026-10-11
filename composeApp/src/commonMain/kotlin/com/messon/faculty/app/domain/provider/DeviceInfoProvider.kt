package com.messon.faculty.app.domain.provider

import com.messon.faculty.app.domain.dataModel.DeviceInfo

interface DeviceInfoProvider {
    suspend fun getDeviceInfo(): DeviceInfo
}