package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentTestBinding

class TestFragment : BaseFragment<BaseViewModel, FragmentTestBinding>() {

    companion object{
        fun newInstance(msg: String): TestFragment {
            val args = Bundle()

            val fragment = TestFragment()
            fragment.arguments = args
            args.putString("message", msg)
            return fragment
        }
    }

    override fun layoutId(): Int = R.layout.fragment_test

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            var msg = it.getString("message")
            mDataBinding.tvMessage.text = msg
        }
    }
}