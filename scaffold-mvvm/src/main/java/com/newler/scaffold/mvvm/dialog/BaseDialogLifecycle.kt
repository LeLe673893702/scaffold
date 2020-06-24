package com.newler.scaffold.base.dialog

import com.newler.scaffold.mvvm.BaseViewLifecycle

/**
 *
 * @what 对话框生命周期
 * @author 17173
 * @date 2020/1/15
 *
 */
interface BaseDialogLifecycle<ViewModel> : BaseViewLifecycle<ViewModel> {

    /**
     * 用于设置Dialog样式、大小等
     */
    fun initDialog()
}