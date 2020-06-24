package com.newler.scaffold.base.state

import com.newler.scaffold.base.BaseView

interface BaseStateView : BaseView {
    fun showLoadFailed()
    fun showContent()
    fun showLoading()
    fun showEmpty()
}