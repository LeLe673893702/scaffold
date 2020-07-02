package com.newler.scaffold.mvvm.state

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.newler.scaffold.mvvm.BaseFragment
import com.newler.state.StateManager
import com.newler.state.ViewState

/**
 *
 * MVVM页面状态管理基类Fragment
 * @author newler
 * @date 2020/6/23
 *
 */
abstract class BaseStateFragment<ViewModel : BaseStateViewModel> : BaseFragment<ViewModel>() {

    // 点击返回键监听回调
    protected var backCallback : OnBackPressedCallback? = null

    protected open val holder by lazy {
        view?.let {
            StateManager.instance.wrap(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(),container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel?.onLoadData()

        withRetryListener()

        backCallback = requireActivity().onBackPressedDispatcher.addCallback {
            try {
                findNavController().popBackStack()
            } catch (e: Exception) {
            }
        }
    }

    override fun observerData() {
        observerPageState()
    }

    protected open fun observerPageState() {
        mViewModel?.viewState?.observe(viewLifecycleOwner, Observer {
            when(it) {
                ViewState.CONTENT -> holder?.showContent()
                ViewState.EMPTY_DATA -> holder?.showEmpty()
                ViewState.LOADING -> holder?.showLoading()
                else -> holder?.showLoadFailed()
            }
        })
    }

    protected open fun withRetryListener() {
        holder?.withRetryListener(Runnable {
            mViewModel?.onRetry()
        })
    }
}