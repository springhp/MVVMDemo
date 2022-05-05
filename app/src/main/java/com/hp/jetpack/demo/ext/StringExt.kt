package com.hp.jetpack.demo.ext

import android.os.Parcelable
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils

fun String.logE(){
    LogUtils.e(this)
}

fun String.logI(){
    LogUtils.i(this)
}

fun String.logD(){
    LogUtils.d(this)
}

fun Parcelable.logE(){
    GsonUtils.toJson(this).logE()
}
