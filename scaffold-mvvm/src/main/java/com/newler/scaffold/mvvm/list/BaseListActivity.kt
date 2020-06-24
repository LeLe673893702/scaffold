package com.newler.scaffold.mvvm.list

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.newler.scaffold.mvvm.state.BaseStateActivity
import com.newler.scaffold_mvvm.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *
 * 列表基类Activity
 * @author liule
 * @date 2020/6/23
 *
 */
abstract class BaseListActivity<ViewModel: BaseListViewModel> : BaseStateActivity<ViewModel>() {
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

    protected open fun getLayoutManager() :RecyclerView.LayoutManager {
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