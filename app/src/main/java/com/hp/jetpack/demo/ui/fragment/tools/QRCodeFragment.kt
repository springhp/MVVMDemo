package com.hp.jetpack.demo.ui.fragment.tools

import android.graphics.Color
import android.os.Bundle
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.QrManager
import cn.bertsir.zbar.view.ScanLineView
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentQrCodeBinding
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.nav


class QRCodeFragment : BaseFragment<BaseViewModel, FragmentQrCodeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click = ProxyClick()

        arguments?.let {
            LogUtils.e(it.get("test"))
        }
        mDataBinding.toolbar.initClose(titleStr = "二维码") {
            nav().navigateUp()
        }
//        mDataBinding.toolbar.run {
//            title = "二维码扫描"
//            mActivity.setSupportActionBar(this)
//        }
    }

    override fun layoutId(): Int = R.layout.fragment_qr_code

    inner class ProxyClick{

        fun openQrcode() {
            val qrConfig: QrConfig = QrConfig.Builder()
                .setDesText("(识别二维码)") //扫描框下文字
                .setShowDes(false) //是否显示扫描框下面文字
                .setShowLight(true) //显示手电筒按钮
                .setShowTitle(true) //显示Title
                .setShowAlbum(true) //显示从相册选择按钮
                .setCornerColor(Color.WHITE) //设置扫描框颜色
                .setLineColor(Color.WHITE) //设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM) //设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE) //设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE) //设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25) //此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true) //是否扫描成功后bi~的声音
                .setNeedCrop(false) //从相册选择二维码之后再次截取二维码
                //.setDingPath(R.raw.test) //设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true) //是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码") //设置Tilte文字
                .setTitleBackgroudColor(R.color.colorPrimary) //设置状态栏颜色
                .setTitleTextColor(R.color.colorT1) //设置Title文字颜色
                .setShowZoom(false) //是否手动调整焦距
                .setAutoZoom(true) //是否自动调整焦距
                .setFingerZoom(true) //是否开始双指缩放
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT) //设置屏幕方向
                .setDoubleEngine(false) //是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setOpenAlbumText("选择要识别的图片") //打开相册的文字
                .setLooperScan(false) //是否连续扫描二维码
                .setLooperWaitTime(5 * 1000) //连续扫描间隔时间
                .setScanLineStyle(ScanLineView.style_radar) //扫描动画样式
                .setAutoLight(false) //自动灯光
                .setShowVibrator(false) //是否震动提醒
                .create()
            QrManager.getInstance().init(qrConfig)
                .startScan(mActivity) { result ->
                    LogUtils.e("onScanSuccess: ${result.content}")
                }
        }
    }


}