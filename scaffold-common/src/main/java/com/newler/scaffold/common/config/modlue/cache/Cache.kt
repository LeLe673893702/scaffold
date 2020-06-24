package com.newler.scaffold.common.config.modlue.cache

import androidx.annotation.Nullable

/**
 *
 * @what
 * @author newler
 * @date 2020/2/14
 *
 */
interface Cache<K, V> {
    interface Factory {
        /**
         * 根据type返回Cache
         */
        @Nullable
        fun <K,V>build(type: CacheType): Cache<K, V>
    }

    /**
     * 返回当前缓存占用的总size
     */
    @Nullable
    fun size(): Int

    /**
     * 返回当前缓存所能允许的最大size
     */
    @Nullable
    fun getMaxSize():Int

    /**
     * 返回缓存中key对应的value
     */
    @Nullable
    fun get(key: K) : V?

    /**
     * 插入KV键值对
     */
    @Nullable
    fun put(key: K, value: V): V?

    /**
     * 根据key移除value
     */
    @Nullable
    fun remove(key: K): V?

    /**
     * 是否有对应的value
     */
    fun containsKey(key: K): Boolean

    /**
     * 返回当前缓存所有的key
     */
    fun keySet() : MutableSet<K>

    /**
     * 清除缓存所有的内容
     */
    fun clear()
}