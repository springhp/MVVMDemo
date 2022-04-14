package com.hp.jetpack.demo.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.hp.jetpack.demo.network.ApiService
import com.hp.jetpack.demo.weight.loadCallBack.EmptyCallback
import com.hp.jetpack.demo.weight.loadCallBack.ErrorCallback
import com.hp.jetpack.demo.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        MMKV.initialize(this)

        CrashReport.initCrashReport(this, "c849d7a0-6d0e-46e1-8c82-3934b7b0f31a", true);

        RetrofitUrlManager.getInstance().putDomain("zongheng", ApiService.ZONG_HENG)
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }
}