package com.hp.jetpack.demo.ui.widget

import android.os.Bundle
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.ActivityRoomBinding

class ListWidgetConfigureActivity :BaseActivity<BaseViewModel,ActivityRoomBinding>() {

    override fun layoutId(): Int  = R.layout.activity_list_widget

    override fun createObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}