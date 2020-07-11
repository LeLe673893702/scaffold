package com.newler.scaffold.mvvm.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.LiveData
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
abstract class BaseStateFragment<ViewModel : BaseStateViewModel> : BaseFragment<ViewModel>(), BaseStateView {

    // 点击返回键监听回调
    protected var backCallback : OnBackPressedCallback? = null

    protected open var holder : StateManager.Holder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(),container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holder = StateManager.instance.wrap(view)

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
        mViewModel?.viewState?.let { observerPageState(it) }
    }

    protected open fun observerPageState(viewState: LiveData<Int>) {
        viewState.observe(this, Observer {
            when(it) {
                ViewState.CONTENT -> showContent()
                ViewState.EMPTY_DATA -> showEmpty()
                ViewState.LOADING -> showLoading()
                else -> showLoadFailed()
            }
        })
    }

    protected open fun withRetryListener() {
        holder?.withRetryListener(Runnable {
            mViewModel?.onRetry()
        })
    }

    override fun showContent() {
        holder?.showContent()
    }

    override fun showEmpty() {
        holder?.showEmpty()
    }

    override fun showLoadFailed() {
        holder?.showLoading()
    }

    override fun showLoading() {
        holder?.showLoading()
    }
}