package com.newler.scaffold

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 *
 * @what
 * @author 17173
 * @date 2020/7/11
 *
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}