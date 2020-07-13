package com.newler.scaffold

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newler.scaffold.mvvm.state.BaseStateFragment
import com.newler.scaffold.mvvm.state.BaseStateViewModel
import com.newler.state.Gloading
import com.newler.state.StateManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : BaseStateFragment<BaseStateViewModel>() {


    override fun initView() {
        holder?.showContent()
        holder?.showLoading()
    }

    override fun registerEvent() {
    }

    override fun unregisterEvent() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_blank

    override fun getViewModel(): BaseStateViewModel? = null

    override fun observerEvent() {
    }


}