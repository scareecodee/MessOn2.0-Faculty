package com.messon.faculty.app

import android.app.Application
import com.messon.faculty.app.core.AndroidContextHolder
import com.messon.faculty.app.di.platformModule
import com.messon.faculty.app.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.mp.KoinPlatform.getKoin

class MessonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
        AndroidContextHolder.init(this)
        startKoin {
            androidContext(this@MessonApp)
            modules(sharedModule, platformModule)
        }
    }
}