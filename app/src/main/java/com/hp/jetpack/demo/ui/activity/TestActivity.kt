package com.hp.jetpack.demo.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.ActivityTestBinding
import com.hp.jetpack.demo.ext.initClose

class TestActivity : BaseActivity<BaseViewModel, ActivityTestBinding>() {

    override fun layoutId(): Int = R.layout.activity_test

    override fun createObserver() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.btnReturn.setOnClickListener {
            var intent = Intent().apply {
                putExtra("return_data","返回的数据")
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        mDataBinding.toolbar.initClose(titleStr = "测试", backImg = R.drawable.ic_back, onBack = {
            finish()
        })
    }

}