package com.newler.scaffold

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.newler.scaffold.utils.ResourcesUtil
import com.newler.state.Gloading
import com.newler.state.StateManager
import com.newler.state.ViewState
import com.qmuiteam.qmui.widget.QMUIEmptyView

/**
 *
 * @what
 * @author newler
 * @date 2020/2/17
 *
 */
class GlobalStatusView(context:Context,
                       var viewState:Int?,
                       var holder: Gloading.Holder
) : QMUIEmptyView(context) {

    init {
        showState()
    }

    private fun showState() {
        when(viewState) {
            ViewState.LOADING ->
                show(true)
            ViewState.EMPTY_DATA ->
                show("空的", null)
            ViewState.LOAD_FAILED ->
                show(false,
                    "加载中",
                    null,
                    "点击重试"
                ) {
//                    holder.getViewStateListener(ViewState.LOAD_FAILED)?.run()
                }
        }
    }

}