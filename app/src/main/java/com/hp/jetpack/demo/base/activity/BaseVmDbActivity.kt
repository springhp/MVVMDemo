package com.hp.jetpack.demo.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hp.jetpack.demo.base.viewmodel.BaseDataViewModel
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel

abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    lateinit var mDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        mDataBinding = DataBindingUtil.setContentView(this, layoutId())
        mDataBinding.lifecycleOwner = this
    }

    override fun createObserver() {
        if (mViewModel is BaseDataViewModel){
            (mViewModel as BaseDataViewModel).error.observe(this){

            }
        }
    }
}