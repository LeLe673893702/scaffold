package com.newler.scaffold.common.config

import android.app.Application
import android.content.Intent
import androidx.annotation.NonNull


/**
 *
 * @what activity管理器
 * @author 17173
 * @date 2020/1/9
 *
 */
class AppManager {
    private lateinit var application: Application
    companion object {
        @JvmStatic
        val instance by lazy {
            AppManager()
        }
    }

    fun init(application: Application) : AppManager {
        this.application = application
        return this
    }

    fun getApp() : Application {
        return application
    }

    /**
     * 查看activity是否存在
     * @param packageName 包名
     * @param activityCls activity类名
     */
    fun isActivityExists(
        @NonNull packageName: String,
        @NonNull activityCls: String
    ): Boolean {
        val intent: Intent = Intent()
        intent.setClassName(packageName, activityCls)
        val packageManager = application.packageManager
        return !(packageManager.resolveActivity(intent, 0) == null ||
                intent.resolveActivity(packageManager) == null ||
                packageManager.queryIntentActivities(intent, 0).size == 0)
    }


}