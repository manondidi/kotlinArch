package com.czq.kotlinarch

import android.app.Application

class App: Application() {
    companion object {
        lateinit var application:App
    }

    override fun onCreate() {
        super.onCreate()
        application=this
    }
}