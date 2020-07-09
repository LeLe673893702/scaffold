package com.newler.scaffold.mvvm.state

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.newler.scaffold.mvvm.BaseActivity
import com.newler.state.StateManager
import com.newler.state.ViewState

/**
 * MVVM页面状态管理基类Activity
 * @author newler
 * @date 2020/6/23
 *
 */
abstract class BaseStateActivity<ViewModel: BaseStateViewModel> : BaseActivity<ViewModel>(), BaseStateView {

    protected open val holder by lazy {
        StateManager.instance.wrap(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel?.onLoadData()

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

    override fun showContent() {
        holder?.showContent()
    }

    override fun showEmpty() {
        holder?.showEmpty()
    }

    override fun showLoadFailed() {
        holder?.showLoadFailed()
    }

    override fun showLoading() {
        holder?.showLoading()
    }

    protected open fun withRetryListener() {
        holder?.withRetryListener(Runnable {
            mViewModel?.onRetry()
        })
    }

}