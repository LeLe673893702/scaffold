package com.newler.scaffold.base

import androidx.fragment.app.FragmentActivity
import com.uber.autodispose.AutoDisposeConverter

interface BaseView {
    fun <T> autoDispose(): AutoDisposeConverter<T>
    fun <T> disposeOnDestroy(): AutoDisposeConverter<T>
    fun attainActivity(): FragmentActivity?
}