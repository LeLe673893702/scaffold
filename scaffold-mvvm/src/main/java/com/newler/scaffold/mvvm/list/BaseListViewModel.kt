package com.newler.scaffold.mvvm.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newler.scaffold.mvvm.state.BaseStateViewModel

/**
 *
 * 列表fragment管理类
 * @author liule
 * @date 2020/6/23
 *
 */
abstract class BaseListViewModel : BaseStateViewModel() {

    abstract fun onRefresh()

    abstract fun onLoadMore()
}

