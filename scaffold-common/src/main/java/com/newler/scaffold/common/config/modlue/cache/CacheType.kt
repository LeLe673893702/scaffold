package com.newler.scaffold.common.config.modlue.cache

import android.app.Activity
import android.app.ActivityManager
import android.content.Context

/**
 *
 * @what
 * @author newler
 * @date 2020/2/14
 *
 */
interface CacheType {
    companion object {
        const val GLOBAL_EXTRAS_TYPE_ID = 2
        const val ACTIVITY_CACHE_TYPE_ID = 3
        const val FRAGMENT_CACHE_TYPE_ID = 4

        /**
         * [Application] 全局存储容器
         */
        var GLOBAL_EXTRAS: CacheType = object : CacheType {
            private val MAX_SIZE = 500
            private val MAX_SIZE_MULTIPLIER = 0.005f
            override fun getCacheTypeId(): Int {
                return GLOBAL_EXTRAS_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager =
                    context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize =
                    (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }

        /**
         * [Activity] 中存储数据的容器，存储Activity页面数据
         */
        var ACTIVITY_CACHE: CacheType = object : CacheType {
            private val MAX_SIZE = 80
            private val MAX_SIZE_MULTIPLIER = 0.0008f
            override fun getCacheTypeId(): Int {
                return ACTIVITY_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager =
                    context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize =
                    (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }

        /**
         * [Fragment] 中存储数据的容器，存储Fragment页面数据
         */
        var FRAGMENT_CACHE: CacheType = object : CacheType {
            private val MAX_SIZE = 80
            private val MAX_SIZE_MULTIPLIER = 0.0008f
            override fun getCacheTypeId(): Int {
                return FRAGMENT_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager =
                    context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize =
                    (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }
    }



    /**
     * 返回框架内需要缓存的模块对应的 `id`
     *
     * @return
     */
    fun getCacheTypeId(): Int

    /**
     * 计算对应模块需要的缓存大小
     *
     * @return
     */
    fun calculateCacheSize(context: Context): Int
}