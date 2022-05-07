package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentRegisterBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.model.LoginViewModel

class RegisterFragment : BaseFragment<LoginViewModel, FragmentRegisterBinding>() {
    override fun layoutId(): Int = R.layout.fragment_register

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                nav().navigateUp()
            }
            title = "注册"
        }
    }

    inner class ProxyClick {
        fun register() {
            mViewModel.register()
        }

        fun login() {
        }
    }
}