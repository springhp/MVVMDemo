package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentLoginBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.showMessage
import com.hp.jetpack.demo.model.LoginViewModel
import com.hp.jetpack.demo.util.CacheUtil

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    inner class ProxyClick {
        fun register() {
            nav().navigate(R.id.register_fragment)
        }

        fun login() {
            KeyboardUtils.hideSoftInput(mActivity)
            when {
                mViewModel.nameField.get().length < 3 -> showMessage("请输入用户名")
                mViewModel.passwordField.get().length < 6 -> showMessage("请输入正确的密码")
                else ->
                    mViewModel.login()
            }

        }
    }

    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click = ProxyClick()
        mDataBinding.model = mViewModel

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
        mViewModel.loginState.observe(viewLifecycleOwner) {
            if (it.isSuccess()) {
                CacheUtil.setUser(it.data)
                nav().navigateUp()
            }else{
                ToastUtils.showLong(it.message)
            }
        }
    }
}