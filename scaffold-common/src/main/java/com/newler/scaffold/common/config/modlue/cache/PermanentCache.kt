package com.newler.scaffold.common.config.modlue.cache


/**
 *
 * @what 参考jessYan MvpArms Cache，是用cacheMap和lruCache两种方式
 * cacheMap永久保存键值对直到, 应用被杀死
 * @author newler
 * @date 2020/2/14
 *
 */
class PermanentCache<K, V>(size: Int) : Cache<K, V> {
    private var cacheMap: MutableMap<K, V> = HashMap(size)

    override fun size() = cacheMap.size

    override fun getMaxSize() = cacheMap.size

    /**
     * 根据key返回value
     */
    override fun get(key: K): V? {
        return cacheMap[key]
    }

    /**
     * 插入key，value
     */
    override fun put(key: K, value: V): V? {
        return cacheMap.put(key, value)
    }

    /**
     * 根据key，移除value
     */
    override fun remove(key: K): V? {
        return cacheMap.remove(key)
    }

    /**
     * 是否包含key
     */
    override fun containsKey(key: K): Boolean {
        return cacheMap.containsKey(key)
    }

    /**
     * 返回[lruCache]和[cacheMap]所有的键值
     */
    override fun keySet(): MutableSet<K> {
        return cacheMap.keys
    }

    override fun clear() {
        cacheMap.clear()
    }
}