package com.newler.scaffold.mvvm.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.newler.scaffold.mvvm.state.BaseStateActivity
import com.newler.scaffold_mvvm.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *
 * 列表基类Fragment
 * @author liule
 * @date 2020/6/24
 *
 */
abstract class BaseListFragment<ViewModel: BaseListViewModel> : BaseStateActivity<ViewModel>(), BaseListView {
    protected open val refreshLayout: SmartRefreshLayout? by lazy {
        findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
    }

    protected open val recyclerView: RecyclerView? by lazy {
        findViewById<RecyclerView?>(R.id.recycleView)
    }

    protected open val dataAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter()
    }

    override fun initView() {
        initRecycle()
        initRefreshLayout()
    }

    /**
     * 初始化recyclerview
     */
    private fun initRecycle() {
        recyclerView?.adapter = dataAdapter
        recyclerView?.layoutManager = getLayoutManager()
        registerItemViewBinder(dataAdapter)
    }

    protected open fun getLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(this, RecyclerView.VERTICAL, false)
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
            mViewModel?.onRefresh()
        }
        refreshLayout?.setOnLoadMoreListener {
            mViewModel?.onLoadMore()
        }
    }

    override fun initLoadSucceed(data: List<Any>, isLastPage:Boolean, isFirstPage:Boolean) {
        dataAdapter.items = data
        dataAdapter.notifyDataSetChanged()
        if (data.isEmpty()) {
            showEmpty()
        } else {
            showContent()
        }
        if (isLastPage) {
            refreshLayout?.finishLoadMoreWithNoMoreData()
        }
    }

    override fun refreshSucceed(data: List<Any>, isLastPage:Boolean) {
        dataAdapter.items = data
        dataAdapter.notifyDataSetChanged()

        if (isLastPage) {
            refreshLayout?.finishRefreshWithNoMoreData()
        } else {
            refreshLayout?.finishRefresh(true)
        }
    }

    /**
     * 数据源需要先在外部更新后,再通知列表更新数据,adapter和数据源是一份
     */
    override fun loadMoreSucceed(data: List<Any>, isLastPage:Boolean) {
        val count = data.size
        val start = dataAdapter.items.size - count

        dataAdapter.notifyItemRangeInserted(start, count)
        if (isLastPage) {
            refreshLayout?.finishLoadMoreWithNoMoreData()
        } else {
            refreshLayout?.finishLoadMore(true)
        }
    }

    override fun initLoadFailed() {
        showLoadFailed()
    }

    override fun refreshFailed() {
        refreshLayout?.finishRefresh(false)
    }

    override fun loadMoreFailed() {
        refreshLayout?.finishLoadMore(false)
    }

    /**
     * 注册viewBinder和数据源
     */
    abstract fun registerItemViewBinder(rvAdapter: MultiTypeAdapter)

    /**
     * 是否可以下拉刷新
     */
    protected open fun isRefreshEnable() = true

    /**
     * 是否可以上拉加载
     */
    protected open fun isLoadMoreEnable() = true
}