package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentLoginBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.parseState
import com.hp.jetpack.demo.ext.showMessage
import com.hp.jetpack.demo.lifecycle.MyLifecycleListener
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

    override fun lazyLoadData() {
    }

    override fun initData() {
    }

    override fun createObserver() {
        mViewModel.loginState.observe(this) { state ->
            parseState(state, {
                nav().navigateUp()
            }, {
                showMessage(it.errorMsg)
            })
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.model = mViewModel
        mDataBinding.click = ProxyClick()

        lifecycle.addObserver(MyLifecycleListener("LOGIN"))
    }
}