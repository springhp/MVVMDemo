package com.hp.jetpack.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hp.jetpack.demo.base.activity.BaseVmActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel

class MainActivity : BaseVmActivity<BaseViewModel>() {
    override fun layoutId() = R.layout.activity_main

    override fun createObserver() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        //TODO 延时加载初始化 meta-data当中加入了一个tools:node="remove"的标记
        //AppInitializer.getInstance(this).initializeComponent(LitePalInitializer::class.java)
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }
}