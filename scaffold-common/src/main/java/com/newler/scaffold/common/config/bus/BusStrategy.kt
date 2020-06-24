package com.newler.scaffold.common.config.bus

/**
 *
 * @what bus选择策略，可以选择配置RxBus或者EventBus，如果不实现该策略则默认不使用bus
 * @author 17173
 * @date 2020/1/8
 *
 */
interface BusStrategy {
    fun register(obj: Any)
    fun unregister(obj: Any)
    fun post(obj: Any)
    fun post(tag: String, obj: Any)
}