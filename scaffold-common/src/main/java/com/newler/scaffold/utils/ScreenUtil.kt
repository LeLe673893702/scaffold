package com.newler.scaffold.utils

import android.content.Context
import androidx.annotation.Nullable
import com.newler.scaffold.common.config.AppManager

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/9
 *
 */
class ScreenUtil {
    companion object {
        private val resources  by lazy {
            AppManager.instance.getApp().resources
        }

        fun dp2px(@Nullable dpValue: Float) : Int {
            val scale: Float = resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2db(@Nullable pxValue: Int):Int {
            val scale: Float = resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun getScreenWidth(): Int {
            return resources.displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return resources.displayMetrics.heightPixels
        }
    }
}