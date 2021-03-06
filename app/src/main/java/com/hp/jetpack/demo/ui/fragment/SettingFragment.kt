package com.hp.jetpack.demo.ui.fragment

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.biometrics.BiometricPrompt
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.customListAdapter
import com.blankj.utilcode.util.*
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.result.EnterpriseBean
import com.hp.jetpack.demo.databinding.FragmentSettingBinding
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.model.SettingViewModel
import com.hp.jetpack.demo.ui.activity.RoomActivity
import com.hp.jetpack.demo.ui.activity.TestActivity
import com.hp.jetpack.demo.ui.activity.WorkManagerActivity
import com.hp.jetpack.demo.ui.adapter.EnterpriseAdapter
import com.hp.jetpack.demo.util.DialogUtils
import com.hp.jetpack.demo.util.MySpUtils

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {
    override fun layoutId(): Int = R.layout.fragment_setting
    lateinit var soundPool: SoundPool
    var soundID = 0
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.toolbar.initClose{
            nav().navigateUp()
        }
        soundPool = SoundPool.Builder().apply {
            setMaxStreams(2) // //??????????????????????????????
            val attrBuilder: AudioAttributes.Builder = AudioAttributes.Builder()
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC)
            setAudioAttributes(attrBuilder.build())
        }.build()
        soundID = soundPool.load(context, cn.bertsir.zbar.R.raw.qrcode, 1)

        LogUtils.e(soundID)

        immersionBar {
        }

        mDataBinding.click = ProxyClick()
        mDataBinding.llEnterprise.setOnClickListener {
            mViewModel.enterpriseBeanList.value ?: mViewModel.getEnterprise()
            mViewModel.enterpriseBeanList.value?.let {
                if (it.data.isEmpty()) {
                    mViewModel.getEnterprise()
                } else {
                    showAdapterMaterialDialog(it.data.toMutableList())
                }
            }
        }

        mDataBinding.tvEnterprise.text = MySpUtils.enterpriseName

        mDataBinding.tvVersion.text = AppUtils.getAppVersionName()

    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.enterpriseBeanList.observe(viewLifecycleOwner) {
            if (it.isSuccess()) {
                if (it.data.isEmpty()) {
                    ToastUtils.showShort("????????????")
                } else {
                    showAdapterMaterialDialog(it.data.toMutableList())
                }
            } else {
                ToastUtils.showShort(it.message)
            }
        }
    }

    private fun showAdapterMaterialDialog(data: MutableList<EnterpriseBean>) {
        val adapter = EnterpriseAdapter(data)

        val dialog = MaterialDialog(mActivity, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customListAdapter(
                adapter,
                LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            )
            lifecycleOwner(this@SettingFragment)
        }
        adapter.setOnItemClickListener { _, _, position ->
            val bean = data[position]
            MySpUtils.enterpriseID = bean.id
            MySpUtils.enterpriseName = bean.text
            mDataBinding.tvEnterprise.text = bean.text
            dialog.dismiss()
        }

    }

    inner class ProxyClick {
        fun downloadApp() {
            val request = DownloadManager.Request(Uri.parse("https://www.baidu.com")).apply {
                setAllowedOverRoaming(true)//???????????????????????????????????????
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                setTitle("???????????????")
                setDescription("????????????????????????")
            }
            val downloadManager =
                ContextCompat.getSystemService(mActivity, DownloadManager::class.java)
            val dwnId = downloadManager?.enqueue(request)
            LogUtils.e("==${dwnId}")
            mActivity.registerReceiver(
                DownloadBroadcastReceiver(),
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }

        fun logout() {
            nav().navigate(R.id.login_fragment)
        }

        fun soundPool() {
            //???????????????,?????????????????????
            soundPool.play(soundID, 1F, 1F, 1, 0, 1F)
            //soundPool.release()
        }

        fun share() {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "??????")
                putExtra(Intent.EXTRA_TEXT, "???????????????????????????")
            })
        }

        fun lineChange() {
            // ?????????????????????????????????
            ToastUtils.showShort("??????????????????")
            DialogUtils.showCzDialog(activity) {

            }.show()
            //RetrofitUrlManager.getInstance().setGlobalDomain("")
        }

        fun clearCache() {

            // TODO: ???????????????????????????????????????????????????
            val size = CacheDiskUtils.getInstance().cacheSize
            context?.let {
                MaterialDialog(it).show {
                    title(R.string.clear_cashe)
                    message(text = "????????????:${size}M")
                    positiveButton(R.string.btn_ok) {
                        LogUtils.e("??????")
                    }
                    cancelOnTouchOutside(false)
                    negativeButton(R.string.btn_cancel) {
                        LogUtils.e("??????")
                    }
                    lifecycleOwner(this@SettingFragment)
                }


//                MaterialDialog(it).show {
//                    title(R.string.clear_cashe)
//                    input(waitForPositiveButton = false, hint = "????????????") { dialog, text ->
//                        // Text changed
//                    }
//                    positiveButton(R.string.btn_ok)
//                    positiveButton(R.string.btn_cancel)
//                lifecycleOwner(this@SettingFragment)
//                }
//
//                MaterialDialog(it).show {
//                    fileChooser(context = it) { dialog, file ->
//                        // File selected
//                    }
//                    lifecycleOwner(this@SettingFragment)
//                }
            }


//            AlertDialog.Builder(mActivity).apply {
//                setTitle("????????????")
//                setCancelable(true)
//                setMessage("????????????:${size}M")
//                setNegativeButton("??????") { _, _ -> CacheDiskUtils.getInstance().clear() }
//                setPositiveButton("??????", null)
//            }.show()
        }

        fun modeChange() {
            //MODE_NIGHT_NO ????????????
            //MODE_NIGHT_YES ????????????
            //MODE_NIGHT_FOLLOW_SYSTEM ????????????
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            LogUtils.e(AppCompatDelegate.getDefaultNightMode())
        }

        @RequiresApi(Build.VERSION_CODES.P)
        fun js() {

            val cancellationSignal = CancellationSignal()
            BiometricPrompt.Builder(Utils.getApp()).apply {
                setTitle("test")
                setNegativeButton("??????", getMainExecutor(mActivity)) { listener, z ->
                    LogUtils.e("$z")
                }
            }.build().authenticate(
                cancellationSignal, getMainExecutor(mActivity),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        LogUtils.e(errString)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        LogUtils.e("onAuthenticationFailed")
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        LogUtils.e("onAuthenticationSucceeded")
                    }
                }
            )


        }

        fun activityResult() {
            launch.launch(
                Intent(
                    mActivity, TestActivity::
                    class.java
                )
            )
        }

        fun workManager() {
            startActivity(Intent(mActivity, WorkManagerActivity::class.java))
        }

        fun room() {
            startActivity(Intent(mActivity, RoomActivity::class.java))
        }

        fun gotoMine() {
            nav().navigate(R.id.mine_fragment)
        }

        fun smartRefreshLayout() {
            nav().navigate(R.id.smart_refresh_layout_fragment)
        }
    }

    var launch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            var intent = it.data;
            intent?.let { i ->
                LogUtils.e(i.getStringExtra("return_data"))
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

//TODO ??????????????????
class DownloadBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val dwnId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
            // TODO: ???????????????????????????
            LogUtils.e(dwnId)
        }
    }
}

