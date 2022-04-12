package com.hp.jetpack.demo.base.activity

import androidx.databinding.ViewDataBinding
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.ext.dismissLoadingExt
import com.hp.jetpack.demo.ext.showLoadingExt

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM,DB>() {

    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    override fun dismissLoading() {
        dismissLoadingExt()
    }

}