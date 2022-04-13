package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentDataBinding
import com.hp.jetpack.demo.model.DataViewModel
import kotlinx.android.synthetic.main.fragment_data.*

class DataFragment : BaseFragment<DataViewModel, FragmentDataBinding>() {
    override fun layoutId(): Int = R.layout.fragment_data

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.model = mViewModel

        mDataBinding.btnSwitch.setOnClickListener {
            var data = mapOf("enterpriseId" to "3")
            mViewModel.loadData(GsonUtils.toJson(data))
        }
        mViewModel.logs.set("bbbbb")
    }

    override fun initData() {
        super.initData()

    }

    override fun createObserver() {
        super.createObserver()
    }
}