package com.newler.scaffold.base.list

import androidx.annotation.Nullable
import com.drakeet.multitype.MultiTypeAdapter
import com.newler.scaffold.base.state.BaseStateView

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/14
 *
 */
interface BaseListView :BaseStateView {

    fun refreshList(items: List<Any>)

    fun loadMoreList(items: List<Any>, start: Int, count: Int)

    fun finishRefreshing(succeed: Boolean)

    fun finishLoadMore(succeed: Boolean)

    fun setRefreshEnable(enable: Boolean)

    fun setLoadMoreEnable(enable: Boolean)

    fun onNoMoreData()

    fun isRefreshEnable(): Boolean

    fun isLoadMoreEnable(): Boolean

    fun notifyItemChanged(position: Int)

    fun notifyItemChanged(position: Int, @Nullable payload: Any?)

    fun notifyItemRangeChanged(start: Int, count: Int)

    fun notifyItemRangeChanged(
        start: Int,
        count: Int, @Nullable payload: Any?
    )

    fun notifyItemInserted(position: Int)

    fun notifyItemRangeInserted(start: Int, count: Int)

    fun notifyItemRemoved(position: Int)

    fun notifyItemRangeRemoved(start: Int, count: Int)

    fun notifyDataSetChanged()

    fun setItems(items: List<Any>)

    fun getAdapter(): MultiTypeAdapter?
}