package com.newler.scaffold.base.list

import com.newler.scaffold.base.state.BaseStatePresenter

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/14
 *
 */
interface BaseListPresenter: BaseStatePresenter {
    fun onRefresh()
    fun onLoadMore()
}