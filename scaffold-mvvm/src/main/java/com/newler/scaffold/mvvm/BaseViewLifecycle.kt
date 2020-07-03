package com.newler.scaffold.mvvm

import androidx.annotation.LayoutRes
import androidx.annotation.Nullable

/**
 *
 * @what view的生命周期
 * @author 17173
 * @date 2020/1/15
 *
 */
interface BaseViewLifecycle<ViewModel> {
    fun initView()

    fun registerEvent()

    fun unregisterEvent()

    @LayoutRes
    @Nullable
    fun getLayoutId(): Int

    fun getViewModel():ViewModel?

    fun observerData()

    fun observerEvent()
}