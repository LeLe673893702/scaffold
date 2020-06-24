package com.newler.scaffold.mvp

import com.hwangjr.rxbus.RxBus
import com.newler.scaffold.common.config.bus.BusStrategy


/**
 *
 * @what
 * @author 17173
 * @date 2020/1/9
 *
 */
class RxBusStrategy : BusStrategy {
    override fun register(obj: Any) {
        RxBus.get().register(obj)
    }

    override fun unregister(obj: Any) {
        RxBus.get().unregister(obj)
    }

    override fun post(obj: Any) {
        RxBus.get().post(obj)
    }

    override fun post(tag: String, obj: Any) {
        RxBus.get().post(tag, obj)
    }
}