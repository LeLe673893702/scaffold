package com.newler.scaffold.base.state

import android.os.Bundle
import com.newler.scaffold.base.BaseActivity
import com.newler.scaffold.base.BasePresenter
import com.newler.state.StateManager

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
abstract class BaseStateActivity<T : BaseStatePresenter> : BaseActivity<T>() {
    protected open val holder by lazy {
        StateManager.instance.wrap(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter?.onLoadData()

        withRetryListener()
    }

    protected open fun withRetryListener() {
        holder?.withRetryListener(Runnable {
            mPresenter?.onRetry()
        })
    }

    fun showLoadFailed() {
        holder?.showLoadFailed()
    }

    fun showContent() {
        holder?.showContent()
    }

    fun showLoading() {
        holder?.showLoading()
    }

    fun showEmpty() {
        holder?.showEmpty()
    }

}