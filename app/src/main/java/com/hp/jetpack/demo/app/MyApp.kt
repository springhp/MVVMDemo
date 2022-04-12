package com.hp.jetpack.demo.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.hp.jetpack.demo.weight.loadCallBack.EmptyCallback
import com.hp.jetpack.demo.weight.loadCallBack.ErrorCallback
import com.hp.jetpack.demo.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV

class MyApp :Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        Utils.getApp()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")

        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }
}