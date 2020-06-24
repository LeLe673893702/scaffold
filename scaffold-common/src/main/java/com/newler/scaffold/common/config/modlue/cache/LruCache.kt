package com.newler.scaffold.common.config.modlue.cache

/**
 *
 * @what
 * @author newler
 * @date 2020/2/14
 *
 */
open class LruCache<K,V>(size: Int) : Cache<K, V> {
    private var cache: LinkedHashMap<K, V> = LinkedHashMap(100, 0.75f, true)
    private var initialMaxSize = size
    private var maxSize = size
    private var currentSize: Int = 0

    /**
     * 设置一个系数应用于当时构造函数中所传入的 size, 从而得到一个新的 [.maxSize]
     * 并会立即调用 [.evict] 开始清除满足条件的条目
     *
     * @param multiplier 系数
     */
    @Synchronized
    fun setSizeMultiplier(multiplier: Float) {
        require(multiplier >= 0) { "Multiplier must be >= 0" }
        maxSize = Math.round(initialMaxSize * multiplier)
        evict()
    }

    /**
     * 当缓存中已占用的总 size 大于所能允许的最大 size ,会使用  [.trimToSize] 开始清除满足条件的条目
     */
    private fun evict() {
        trimToSize(maxSize)
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时,会开始清除缓存中最近最少使用的条目
     *
     * @param size `size`
     */
    @Synchronized
    protected fun trimToSize(size: Int) {
        var last: Map.Entry<K, V>
        while (currentSize > size) {
            last = cache.entries.iterator().next()
            val toRemove = last.value
            currentSize -= getItemSize(toRemove)
            val key = last.key
            cache.remove(key)
            onItemEvicted(key, toRemove)
        }
    }

    /**
     * 当缓存中有被驱逐的条目时,会回调此方法,默认空实现,子类可以重写这个方法
     *
     * @param key   被驱逐条目的 `key`
     * @param value 被驱逐条目的 `value`
     */
    protected fun onItemEvicted(key: K, value: V) { // optional override
    }


    override fun size() = currentSize

    /**
     * 返回每个 `item` 所占用的 size,默认为1,这个 size 的单位必须和构造函数所传入的 size 一致
     * 子类可以重写这个方法以适应不同的单位,比如说 bytes
     *
     * @param item 每个 `item` 所占用的 size
     * @return 单个 item 的 `size`
     */
    protected fun getItemSize(item: V) = 1


    override fun getMaxSize() = maxSize

    override fun get(key: K): V? = cache[key]

    override fun put(key: K, value: V): V? {
        val itemSize = getItemSize(value)
        if (itemSize >= maxSize) {
            onItemEvicted(key, value)
            return null
        }

        val result = cache.put(key, value)
        if (value != null) {
            currentSize += getItemSize(value)
        }
        if (result != null) {
            currentSize -= getItemSize(result)
        }
        evict()

        return result
    }

    override fun remove(key: K): V? {
        val value = cache.remove(key)
        if (value != null) {
            currentSize -= getItemSize(value)
        }
        return value
    }

    override fun containsKey(key: K) = cache.containsKey(key)

    override fun keySet() = cache.keys

    override fun clear() {
        trimToSize(0)
    }
}