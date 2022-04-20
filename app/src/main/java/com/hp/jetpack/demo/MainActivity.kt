package com.hp.jetpack.demo

import android.Manifest
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.base.activity.BaseVmActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.permissionx.guolindev.PermissionX
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


class MainActivity : BaseVmActivity<BaseViewModel>() {
    override fun layoutId() = R.layout.activity_main

    override fun createObserver() {
    }

    var exitTime = 0L
    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorPrimary)
        }
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

        val uri = URI.create("ws://121.40.165.18:8800")
        var client = JWebSocketClient(uri);
        try {
            client.connectBlocking()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        if (client != null && client.isOpen) {
            LogUtils.e("你好")
            client.send("你好");
        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionX.init(this).permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).request { _, _, _ ->

        }

    }

    class JWebSocketClient(serverUri: URI?) :

        WebSocketClient(serverUri, Draft_6455()) {
        override fun onOpen(handshakedata: ServerHandshake) {
            LogUtils.e("onOpen")
        }

        override fun onMessage(message: String) {
            LogUtils.e("onMessage")
            LogUtils.e(message)
        }

        override fun onClose(code: Int, reason: String, remote: Boolean) {
            LogUtils.e("onClose")
        }

        override fun onError(ex: Exception) {
            LogUtils.e("onError")
        }
    }
}