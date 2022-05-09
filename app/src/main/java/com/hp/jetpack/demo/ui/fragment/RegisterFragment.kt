package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentRegisterBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.showMessage
import com.hp.jetpack.demo.model.LoginViewModel

class RegisterFragment : BaseFragment<LoginViewModel, FragmentRegisterBinding>() {
    override fun layoutId(): Int = R.layout.fragment_register

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click = ProxyClick()
        mDataBinding.model = mViewModel

        mDataBinding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                nav().navigateUp()
            }
            title = "注册"
        }
    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.loginState.observe(viewLifecycleOwner) {
            ToastUtils.showLong(it.message)
            if (it.isSuccess()) {
                clearData()
            }
        }
    }

    private fun clearData() {
        mViewModel.nameField.set("")
        mViewModel.passwordField.set("")
    }

    inner class ProxyClick {
        fun register() {
            KeyboardUtils.hideSoftInput(mActivity)
            when {
                mViewModel.nameField.get().length < 3 -> showMessage("请输入用户名")
                mViewModel.passwordField.get().length < 6 -> showMessage("请输入正确的密码")
                else ->
                    mViewModel.register()
            }

        }

    }
}