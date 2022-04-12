package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentDataBinding

class DataFragment : BaseFragment<BaseViewModel, FragmentDataBinding>() {
    override fun layoutId(): Int = R.layout.fragment_data

    override fun initView(savedInstanceState: Bundle?) {
    }
}