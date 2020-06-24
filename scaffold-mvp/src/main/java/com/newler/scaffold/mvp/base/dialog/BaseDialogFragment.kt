package com.newler.scaffold.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.newler.scaffold.base.BasePresenter
import com.newler.scaffold.common.config.bus.ScaffoldBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 *
 * @what 基础对话框
 * @author 17173
 * @date 2020/1/15
 *
 */
abstract class BaseDialogFragment<P : BasePresenter> : DialogFragment(), BaseDialogLifecycle<P> {
    var mPresenter:P? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (useBus()) ScaffoldBus.get().register(this)

        mPresenter = getPresenter()

        mPresenter?.let {
            lifecycle.addObserver(it)
            if (useBus()) ScaffoldBus.get().register(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        registerEvent()

        mPresenter?.onStart()
    }

    protected fun show() {
        try {
            activity?.let {
                if (isAdded) {
                    it.supportFragmentManager.beginTransaction().remove(this@BaseDialogFragment).commit()
                }
                show(it.supportFragmentManager, this@BaseDialogFragment::class.java.simpleName)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun useBus() = true

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity() = activity

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