package com.newler.scaffold.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * MVVM基类VM
 * @author newler
 * @date 2020/6/19
 *
 */
abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    abstract fun onStart()
}