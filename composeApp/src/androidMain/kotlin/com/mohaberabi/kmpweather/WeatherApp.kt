package com.mohaberabi.kmpweather

import android.app.Application
import custom.KoinInit

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInit(this).init()
    }
}