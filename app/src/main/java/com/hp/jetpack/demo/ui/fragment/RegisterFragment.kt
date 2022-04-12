package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentRegisterBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.lifecycle.MyLifecycleListener
import kotlinx.android.synthetic.main.fragment_login.*

class RegisterFragment : BaseFragment<BaseViewModel, FragmentRegisterBinding>() {
    override fun layoutId(): Int = R.layout.fragment_register


    override fun initView(savedInstanceState: Bundle?) {
        lifecycle.addObserver(MyLifecycleListener("Register"))
        mDataBinding.btnBack.setOnClickListener {
            nav().navigateUp()
        }
    }

}