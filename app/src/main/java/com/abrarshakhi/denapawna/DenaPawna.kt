package com.abrarshakhi.denapawna

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DenaPawna : Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}