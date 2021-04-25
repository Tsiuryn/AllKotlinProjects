package com.ts.alex.objectbox

import android.app.Application

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        NewsDataBase.init(context = applicationContext)
    }
}