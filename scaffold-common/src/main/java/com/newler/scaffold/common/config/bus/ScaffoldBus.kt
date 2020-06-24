package com.newler.scaffold.common.config.bus

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/13
 *
 */
class ScaffoldBus : BusStrategy {
    private var strategy: BusStrategy?= null
    companion object {
        private val bus: ScaffoldBus by lazy {
            ScaffoldBus()
        }
        fun get(): ScaffoldBus {
            return bus
        }
    }

    fun init(strategy: BusStrategy?) {
        this.strategy = strategy
    }

    override fun register(obj: Any) {
        strategy?.register(obj)
    }

    override fun unregister(obj: Any) {
        strategy?.unregister(obj)
    }

    override fun post(obj: Any) {
        strategy?.post(obj)
    }

    override fun post(tag: String, obj: Any) {
        strategy?.post(tag, obj)
    }
}