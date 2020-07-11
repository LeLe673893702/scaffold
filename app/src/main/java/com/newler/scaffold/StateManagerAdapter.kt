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
class StateManagerAdapter() : Gloading.Adapter {

    override fun getView(holder: Gloading.Holder, convertView: View, status: Int): View {
        holder?.let {
            val globalStatusView =  GlobalStatusView(holder.context, status, it)
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
}