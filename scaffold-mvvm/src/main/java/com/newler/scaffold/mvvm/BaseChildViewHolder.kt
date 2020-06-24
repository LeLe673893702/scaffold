package com.newler.scaffold.base

import android.view.View

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/16
 *
 */
open class BaseChildViewHolder<ChildItemData>(
    private val view: View,
    private val viewHolder: BaseViewHolder<*>
) {
    private var childData: ChildItemData ?= null

    open fun getChildData() = childData

    open fun getParentViewHolder() = viewHolder

    open fun setChildData(childItemData: ChildItemData) {
        this.childData = childItemData
    }
}