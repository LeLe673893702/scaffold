package com.newler.scaffold.mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * MVVM基类Activity
 * @author newler
 * @date 2020/6/23
 *
 */
abstract class BaseActivity<ViewModel: BaseViewModel> : AppCompatActivity(), BaseViewLifecycle<ViewModel> {
    protected open var mViewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel()

        if (getLayoutId() != View.NO_ID) {
            setContentView(getLayoutId())

            mViewModel?.onStart()

            initView()

            registerEvent()
        }
    }

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    override fun onDestroy() {
        unregisterEvent()
        super.onDestroy()
    }
}