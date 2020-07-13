package com.newler.scaffold

import android.view.View
import android.view.ViewGroup
import com.newler.scaffold.utils.ResourcesUtil
import com.newler.state.Gloading
import com.newler.state.StateManager

/**
 *
 * @what
 * @author newler
 * @date 2020/2/17
 *
 */
class StateManagerAdapter() : StateManager.Adapter {
    override fun getView(holder: StateManager.Holder, viewState: Int): View {
        val globalStatusView =  GlobalStatusView(holder.getContext(), viewState, holder)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        globalStatusView.layoutParams = layoutParams
        globalStatusView.isClickable = true
        ResourcesUtil.getColor(R.color.colorPrimary).let {
            globalStatusView.setBackgroundColor(it)
        }
        return globalStatusView
    }


}