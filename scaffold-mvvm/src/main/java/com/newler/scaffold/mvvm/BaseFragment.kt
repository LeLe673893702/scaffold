package com.newler.scaffold.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * MVVM基类fragment
 * @author newler
 * @date 2020/6/23
 *
 */
abstract class BaseFragment<ViewModel: BaseViewModel> : Fragment(), BaseViewLifecycle<ViewModel> {
    protected open var mViewModel: ViewModel? = null
    // 点击返回键监听回调
    protected var backCallback : OnBackPressedCallback? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        registerEvent()

        mViewModel?.onStart()

        observerData()
        observerEvent()

        backCallback = requireActivity().onBackPressedDispatcher.addCallback {
            try {
                findNavController().popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
            }
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