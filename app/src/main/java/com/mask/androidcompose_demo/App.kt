package com.mask.androidcompose_demo

import android.app.Application

/**
 * Application
 *
 * Create by lishilin on 2025/2/18
 */
class App : Application() {

    companion object {
        lateinit var context: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}