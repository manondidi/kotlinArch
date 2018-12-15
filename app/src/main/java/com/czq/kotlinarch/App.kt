package com.czq.kotlinarch

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class App: Application() {
    companion object {
        lateinit var application:App
    }

    override fun onCreate() {
        super.onCreate()
        application=this
        Logger.addLogAdapter( AndroidLogAdapter());
    }
}