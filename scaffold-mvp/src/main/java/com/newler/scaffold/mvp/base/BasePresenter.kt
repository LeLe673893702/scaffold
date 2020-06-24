package com.newler.scaffold.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


interface BasePresenter: LifecycleObserver {
    /**
     * 做一些初始化操作
     */
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}