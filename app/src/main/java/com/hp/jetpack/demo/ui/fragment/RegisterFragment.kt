package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentRegisterBinding
import com.hp.jetpack.demo.ext.nav
import kotlinx.android.synthetic.main.fragment_data.*

class RegisterFragment : BaseFragment<BaseViewModel, FragmentRegisterBinding>() {
    override fun layoutId(): Int = R.layout.fragment_register


    override fun initView(savedInstanceState: Bundle?) {
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                nav().navigateUp()
                throw NullPointerException()
            }
            title = "注册"
        }
    }

}