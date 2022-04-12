package com.hp.jetpack.demo.weight.loadCallBack

import com.hp.jetpack.demo.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}