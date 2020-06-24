package com.newler.scaffold.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.newler.scaffold.common.config.bus.ScaffoldBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseViewLifecycle<P> {
    var mPresenter:P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (useBus()) ScaffoldBus.get().register(this)

        mPresenter = getPresenter()

        mPresenter?.let {
            lifecycle.addObserver(it)
            if (useBus()) ScaffoldBus.get().register(it)
        }

        if (getLayoutId() != View.NO_ID) {
            setContentView(getLayoutId())

            initView()

            registerEvent()

            mPresenter?.onStart()
        }
    }

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity() = this

    fun useBus() = true

    override fun onDestroy() {
        mPresenter?.let {
            lifecycle.removeObserver(it)
            if (useBus()) ScaffoldBus.get().unregister(it)
        }

        if (useBus()) ScaffoldBus.get().unregister(this)
        
        unregisterEvent()
        super.onDestroy()
    }

}