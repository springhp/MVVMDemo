package com.hp.jetpack.demo.init

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class LitePalInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        //TODO
        Log.e("TTTT", "初始化数据")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}