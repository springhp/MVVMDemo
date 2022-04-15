package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentLoginBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.model.LoginViewModel

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    inner class ProxyClick {
        fun register() {
            nav().navigate(R.id.register_fragment)
        }

        fun login() {
            mViewModel.login(mViewModel.nameField.get(), mViewModel.passwordField.get())
        }
    }

    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click = ProxyClick()

        mDataBinding.toolbar.apply {
            title = "登录"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                nav().navigateUp()
            }
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.loginState.observe(this){
            LogUtils.e(it)
        }
    }
}