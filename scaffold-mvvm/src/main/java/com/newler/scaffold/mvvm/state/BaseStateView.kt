package com.newler.scaffold.mvvm.state

/**
 *
 * @what
 * @author 17173
 * @date 2020/7/2
 *
 */
interface BaseStateView {
    fun showContent()

    fun showEmpty()

    fun showLoadFailed()

    fun showLoading()
}