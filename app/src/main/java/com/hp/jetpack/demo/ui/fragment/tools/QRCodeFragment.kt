package com.hp.jetpack.demo.ui.fragment.tools

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentQrCodeBinding

class QRCodeFragment : BaseFragment<BaseViewModel,FragmentQrCodeBinding>(){

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            LogUtils.e(it.get("test"))
        }
        mDataBinding.toolbar.run {
            title = "二维码扫描"
            mActivity.setSupportActionBar(this)
        }
    }

    override fun layoutId(): Int = R.layout.fragment_qr_code

}