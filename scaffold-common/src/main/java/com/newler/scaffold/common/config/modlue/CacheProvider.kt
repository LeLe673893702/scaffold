package com.newler.scaffold.common.config.modlue

import com.newler.scaffold.common.config.modlue.cache.Cache
import com.newler.scaffold.common.config.modlue.cache.CacheType

/**
 *
 * @what cache提供器
 * @author newler
 * @date 2020/2/14
 *
 */
class CacheProvider {
    lateinit var cacheFactory: Cache.Factory
    val globalCache: Cache<String, Any> by lazy {
        cacheFactory.build<String, Any>(CacheType.GLOBAL_EXTRAS)
    }
    companion object {
        @JvmStatic
        val instance : CacheProvider by lazy {
            CacheProvider()
        }
    }

    fun inject(cacheFactory: Cache.Factory) {
        this.cacheFactory = cacheFactory
    }


}