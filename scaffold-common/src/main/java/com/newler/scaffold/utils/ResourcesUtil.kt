package com.newler.scaffold.utils

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.newler.scaffold.common.config.AppManager

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/9
 *
 */
class ResourcesUtil {
    companion object {
        private val app by lazy {
            AppManager.instance.getApp()
        }
        fun getDrawable(@DrawableRes id : Int) : Drawable? {
            return ContextCompat.getDrawable(AppManager.instance.getApp(), id)
        }

        fun getColor(@ColorRes id: Int) : Int {
            return ContextCompat.getColor(app, id)
        }

        fun getDimens(@DimenRes id: Int) : Float {
            return app.resources.getDimension(id)
        }

        fun getString(@StringRes id: Int) : String {
            return app.resources.getString(id)
        }
     }
}