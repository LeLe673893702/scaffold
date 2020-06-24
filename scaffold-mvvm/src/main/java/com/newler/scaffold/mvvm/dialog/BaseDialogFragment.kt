package com.newler.scaffold.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.newler.scaffold.common.config.bus.ScaffoldBus
import com.newler.scaffold.mvvm.BaseViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 *
 * @what 基础对话框
 * @author liule
 * @date 2020/1/15
 *
 */
abstract class BaseDialogFragment<ViewModel: BaseViewModel> : DialogFragment(), BaseDialogLifecycle<ViewModel> {
    protected open var mViewModel : ViewModel? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel()
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

        mViewModel?.onStart()
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


    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    override fun onDetach() {
        unregisterEvent()

        super.onDetach()
    }
}