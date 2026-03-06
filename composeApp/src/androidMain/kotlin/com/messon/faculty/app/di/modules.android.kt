package com.messon.faculty.app.di

import android.app.Activity
import android.content.Context
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.core.auth.GoogleAuth
import com.messon.faculty.app.core.auth.GoogleAuthAndroid
import com.messon.faculty.app.data.remote.network.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformModule=module{
    single { createHttpClient(OkHttp.create()) }

    factory<GoogleAuth> { (activity: Activity) ->
        GoogleAuthAndroid(
            serverClientId = "238348258649-0j2o0hqbhu0pcdrg5i0j1sajg1ppaeeu.apps.googleusercontent.com",
            context = activity
        )
    }


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