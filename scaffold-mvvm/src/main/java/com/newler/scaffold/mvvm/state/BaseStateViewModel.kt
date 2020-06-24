package com.newler.scaffold.mvvm.state

import androidx.lifecycle.MutableLiveData
import com.newler.scaffold.mvvm.BaseViewModel
import com.newler.state.ViewState

/**
 *
 * MVVM页面状态管理ViewModel
 * @author newler
 * @date 2020/6/22
 *
 */
abstract class BaseStateViewModel: BaseViewModel() {
    @ViewState
    val viewState = MutableLiveData<Int>()

    abstract fun onLoadData()

    abstract fun onRetry()
}