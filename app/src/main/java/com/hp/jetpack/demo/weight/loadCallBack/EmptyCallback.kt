package com.hp.jetpack.demo.weight.loadCallBack

import com.hp.jetpack.demo.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}