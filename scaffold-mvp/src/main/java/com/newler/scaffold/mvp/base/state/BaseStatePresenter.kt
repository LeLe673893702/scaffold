package com.newler.scaffold.base.state

import com.newler.scaffold.base.BasePresenter

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
interface BaseStatePresenter : BasePresenter {
    fun onLoadData()
    fun onRetry()
}