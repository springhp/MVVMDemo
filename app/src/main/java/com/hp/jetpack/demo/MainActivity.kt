package com.hp.jetpack.demo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.base.activity.BaseVmActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel

class MainActivity : BaseVmActivity<BaseViewModel>() {
    override fun layoutId() = R.layout.activity_main

    override fun createObserver() {
    }

    var exitTime = 0L
    override fun initView(savedInstanceState: Bundle?) {
        //TODO 延时加载初始化 meta-data当中加入了一个tools:node="remove"的标记
        //AppInitializer.getInstance(this).initializeComponent(LitePalInitializer::class.java)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var nav = Navigation.findNavController(this@MainActivity, R.id.nav_host_nav)
                var currentDestination = nav.currentDestination
                currentDestination?.let {
                    if (currentDestination.id != R.id.main_fragment) {
                        nav.navigateUp()
                    } else {
                        //是主页
                        if (System.currentTimeMillis() - exitTime > 2000) {
                            ToastUtils.showShort("再按一次退出程序")
                            exitTime = System.currentTimeMillis()
                        } else {
                            finish()
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        NetworkUtils.registerNetworkStatusChangedListener(object :
            NetworkUtils.OnNetworkStatusChangedListener {
            override fun onDisconnected() {
                ToastUtils.showShort("没有网络")
            }

            override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                ToastUtils.showShort("网络连接")
            }
        })
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }
}