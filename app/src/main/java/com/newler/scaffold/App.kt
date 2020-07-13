package com.newler.scaffold

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.newler.scaffold.common.config.AppManager

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
        AppManager.instance.init(this)
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}