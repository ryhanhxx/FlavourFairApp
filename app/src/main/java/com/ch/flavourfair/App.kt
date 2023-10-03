package com.ch.flavourfair

import android.app.Application
import com.ch.flavourfair.data.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}
