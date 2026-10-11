package com.messon.faculty.app.di

import com.messon.faculty.app.core.IosPushBridge
import com.messon.faculty.app.core.IosPushTokenProvider
import com.messon.faculty.app.core.PushBridge
import com.messon.faculty.app.core.PushNotificationManager
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.provider.DeviceInfoProvider
import com.messon.faculty.app.domain.provider.IosDeviceInfoProvider
import org.koin.dsl.module

actual val platformModule= module{
    single<AuthSecureStorage> {
        val storage = try {
            AuthSecureStorage()
        } catch (e: Exception) {
            AuthSecureStorage().also { it.clear() }
        }
        storage
    }
    single<PushBridge> { IosPushBridge }
    single { PushNotificationManager() }
    single { IosPushTokenProvider(get()) }
    single<DeviceInfoProvider> { IosDeviceInfoProvider() }
}