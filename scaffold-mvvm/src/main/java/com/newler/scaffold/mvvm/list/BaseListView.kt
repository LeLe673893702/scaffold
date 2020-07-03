package com.newler.scaffold.mvvm.list

/**
 *
 * @what
 * @author 17173
 * @date 2020/7/2
 *
 */
interface BaseListView {
    fun initLoadSucceed(data: List<Any>, isLastPage:Boolean, isFirstPage:Boolean)

    fun refreshSucceed(data: List<Any>, isLastPage:Boolean)

    fun loadMoreSucceed(data: List<Any>, isLastPage:Boolean)

    fun initLoadFailed()

    fun refreshFailed()

    fun loadMoreFailed()
}