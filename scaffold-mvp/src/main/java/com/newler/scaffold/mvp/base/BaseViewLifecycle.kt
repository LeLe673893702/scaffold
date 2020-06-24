package com.newler.scaffold.base

import androidx.annotation.LayoutRes
import androidx.annotation.Nullable

/**
 *
 * @what view的生命周期
 * @author 17173
 * @date 2020/1/15
 *
 */
interface BaseViewLifecycle<P> {
    fun initView()

    fun registerEvent()

    fun unregisterEvent()

    @LayoutRes
    @Nullable
    fun getLayoutId(): Int

    @Nullable
    fun getPresenter() : P

}