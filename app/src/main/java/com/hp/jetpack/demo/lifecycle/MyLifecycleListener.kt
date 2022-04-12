package com.hp.jetpack.demo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils

class MyLifecycleListener(var tag: String) : DefaultLifecycleObserver {


    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtils.e(tag, "onStart")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        LogUtils.e(tag, "onCreate")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        LogUtils.e(tag, "onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        LogUtils.e(tag, "onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtils.e(tag, "onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        LogUtils.e(tag, "onDestroy")
    }

}