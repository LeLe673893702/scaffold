package com.newler.scaffold.mvvm.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

    protected open var holder : StateManager.Holder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container is FragmentContainerView) {
            val wrapper = FrameLayout(requireContext())
            wrapper.layoutParams = FrameLayout.LayoutParams(-1, -1)
            val view = inflater.inflate(getLayoutId(), wrapper, false)
            wrapper.addView(view)
            holder = StateManager.instance.cover(view, wrapper)

            return wrapper
        } else {
            val view = inflater.inflate(getLayoutId(), container, false)
            holder = StateManager.instance.wrap(view)
            return view
        }
    }

    override fun registerEvent() {
        withRetryListener()
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