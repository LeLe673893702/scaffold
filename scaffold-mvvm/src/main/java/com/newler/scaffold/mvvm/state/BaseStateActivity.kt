package com.newler.scaffold.mvvm.state

import android.os.Bundle
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
abstract class BaseStateActivity<ViewModel: BaseStateViewModel> : BaseActivity<ViewModel>() {

    protected open val holder by lazy {
        StateManager.instance.wrap(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel?.onLoadData()

        withRetryListener()

        observerData()
    }

    protected open fun observerData() {
        observerPageState()
    }

    protected open fun observerPageState() {
        mViewModel?.viewState?.observe(this, Observer {
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