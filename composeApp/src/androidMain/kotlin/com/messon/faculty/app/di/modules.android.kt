package com.messon.faculty.app.di

import android.app.Activity
import android.content.Context
import com.messon.faculty.app.core.AndroidPushBridge
import com.messon.faculty.app.core.AndroidPushTokenProvider
import com.messon.faculty.app.core.EncryptedTokenStorage
import com.messon.faculty.app.core.PushBridge
import com.messon.faculty.app.core.PushNotificationManager
import com.messon.faculty.app.core.PushTokenProvider
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.core.auth.GoogleAuthAndroid
import com.messon.faculty.app.data.remote.network.createHttpClient
import com.messon.faculty.app.domain.provider.AndroidDeviceInfoProvider
import com.messon.faculty.app.domain.provider.DeviceInfoProvider
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule=module{
    single { createHttpClient(OkHttp.create()) }
    single<DeviceInfoProvider> { AndroidDeviceInfoProvider(androidContext()) }

    factory<GoogleAuth> { (activity: Activity) ->
        GoogleAuthAndroid(
            serverClientId = "238348258649-0j2o0hqbhu0pcdrg5i0j1sajg1ppaeeu.apps.googleusercontent.com",
            context = activity
        )
    }
    single<PushBridge> { AndroidPushBridge }
    single { EncryptedTokenStorage(androidContext()) }
    single { PushNotificationManager() }
    single < PushTokenProvider>{ AndroidPushTokenProvider(get()) }


    single {
        val context: Context = get()
        try {
            AuthSecureStorage(context)
        } catch (e: Exception) {
            context.deleteSharedPreferences("auth_prefs")
            AuthSecureStorage(context)
        }
    }
}