package com.newler.scaffold.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.newler.scaffold.common.config.bus.ScaffoldBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseViewLifecycle<P> {
    var mPresenter:P? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (useBus()) ScaffoldBus.get().register(this)

        mPresenter = getPresenter()

        mPresenter?.let {
            lifecycle.addObserver(it)
            if (useBus()) ScaffoldBus.get().register(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        registerEvent()

        mPresenter?.onStart()
    }

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity() = activity

    fun useBus() = true

    override fun onDetach() {
        mPresenter?.let {
            lifecycle.removeObserver(it)
            if (useBus()) ScaffoldBus.get().unregister(it)
        }

        if (useBus()) ScaffoldBus.get().unregister(this)

        unregisterEvent()

        super.onDetach()
    }
}