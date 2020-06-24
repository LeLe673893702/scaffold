package com.newler.scaffold.base.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.newler.scaffold.base.state.BaseStateFragment
import com.newler.scaffold_mvp.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *
 * @what 基础列表Fragment
 * @author 17173
 * @date 2020/1/14
 *
 */
abstract class BaseListFragment<P:BaseListPresenter> : BaseStateFragment<P>(), BaseListView {
    protected open val refreshLayout:SmartRefreshLayout? by lazy {
        view?.findViewById<SmartRefreshLayout?>(R.id.smartRefreshLayout)
    }

    protected open val recyclerView: RecyclerView? by lazy {
        view?.findViewById<RecyclerView>(R.id.recycleView)
    }

    protected open val rvAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter()
    }

    override fun initView() {
        initRecycle()
        initRefreshLayout()
    }

    /**
     * 初始化recycleview
     */
    private fun initRecycle() {
        recyclerView?.adapter = rvAdapter
        recyclerView?.layoutManager = getLayoutManager()
        registerItemViewBinder(rvAdapter)
    }

    protected open fun getLayoutManager() :RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    /**
     * 初始化下拉刷新组件
     */
    private fun initRefreshLayout() {
        refreshLayout?.setEnableLoadMore(isLoadMoreEnable())
        refreshLayout?.setEnableRefresh(isRefreshEnable())
    }

    override fun registerEvent() {
        registerRefreshLayoutListener()
    }

    /**
     * 注册下拉加载事件
     */
    protected open fun registerRefreshLayoutListener() {
        refreshLayout?.setOnRefreshListener {
            mPresenter?.onRefresh()
        }
        refreshLayout?.setOnLoadMoreListener {
            mPresenter?.onLoadMore()
        }
    }

    abstract fun registerItemViewBinder(rvAdapter: MultiTypeAdapter)

    override fun refreshList(items: List<Any>) {
        rvAdapter.items = items
        rvAdapter.notifyDataSetChanged()
    }

    override fun loadMoreList(items: List<Any>, start: Int, count: Int) {
        rvAdapter.items = items
        rvAdapter.notifyItemRangeInserted(start, count)
    }

    override fun finishRefreshing(succeed: Boolean) {
        refreshLayout?.finishRefresh(succeed)
    }

    override fun finishLoadMore(succeed: Boolean) {
        refreshLayout?.finishLoadMore(succeed)
    }

    override fun setRefreshEnable(enable: Boolean) {
        refreshLayout?.setEnableRefresh(enable)
    }

    override fun setLoadMoreEnable(enable: Boolean) {
        refreshLayout?.setEnableLoadMore(enable)
    }

    override fun onNoMoreData() {
        refreshLayout?.finishLoadMoreWithNoMoreData()
    }

    override fun isRefreshEnable() = true

    override fun isLoadMoreEnable() = true

    override fun notifyItemChanged(position: Int) {
        rvAdapter.notifyItemChanged(position)
    }

    override fun notifyItemChanged(position: Int, payload: Any?) {
        rvAdapter.notifyItemChanged(position, payload)
    }

    override fun notifyItemRangeChanged(start: Int, count: Int) {
        rvAdapter.notifyItemRangeChanged(start, count)
    }

    override fun notifyItemRangeChanged(start: Int, count: Int, payload: Any?) {
        rvAdapter.notifyItemRangeChanged(start, count, payload)
    }

    override fun notifyItemInserted(position: Int) {
        rvAdapter.notifyItemInserted(position)
    }

    override fun notifyItemRangeInserted(start: Int, count: Int) {
        rvAdapter.notifyItemRangeInserted(start, count)
    }

    override fun notifyItemRemoved(position: Int) {
        rvAdapter.notifyItemRemoved(position)
    }

    override fun notifyItemRangeRemoved(start: Int, count: Int) {
        rvAdapter.notifyItemRangeChanged(start, count)
    }

    override fun notifyDataSetChanged() {
        rvAdapter.notifyDataSetChanged()
    }

    override fun setItems(items: List<Any>) {
        rvAdapter.items = items
    }

    override fun getAdapter() = rvAdapter
}