package com.hp.jetpack.demo.ui.fragment

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.customListAdapter
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.result.EnterpriseBean
import com.hp.jetpack.demo.databinding.FragmentSettingBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.model.SettingViewModel
import com.hp.jetpack.demo.ui.adapter.EnterpriseAdapter
import com.hp.jetpack.demo.util.MySpUtils

class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {
    override fun layoutId(): Int = R.layout.fragment_setting

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.purple_500)
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
                    ToastUtils.showShort("没有数据")
                } else {
                    showAdapterMaterialDialog(it.data.toMutableList())
                }
            } else {
                ToastUtils.showShort(it.message)
            }
        }
    }

    private fun showAdapterMaterialDialog(data: MutableList<EnterpriseBean>) {
        var adapter = EnterpriseAdapter(data)

        var dialog = MaterialDialog(mActivity, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            customListAdapter(adapter)
            lifecycleOwner(this@SettingFragment)
        }

        adapter.setOnItemClickListener() { _, _, position ->
            var bean = data[position]
            MySpUtils.enterpriseID = bean.id
            MySpUtils.enterpriseName = bean.text
            mDataBinding.tvEnterprise.text = bean.text
            dialog.dismiss()
        }

    }

    inner class ProxyClick {
        fun downloadApp() {
            var request = DownloadManager.Request(Uri.parse("https://www.baidu.com")).apply {
                setAllowedOverRoaming(true)//移动网络情况下是否允许漫游
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                setTitle("新版本更新")
                setDescription("正在下载更新文件")
            }
            var downloadManager =
                ContextCompat.getSystemService(mActivity, DownloadManager::class.java)
            var dwnId = downloadManager?.enqueue(request)
            LogUtils.e("==${dwnId}")
            mActivity.registerReceiver(
                DownloadBroadcastReceiver(),
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }

        fun logout() {
            nav().navigate(R.id.login_fragment)
        }

        fun share() {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "分享")
                putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件")
            })
        }

        fun lineChange() {
            // 正式环境，测试环境切换
            ToastUtils.showShort("环境切换成功")
            //RetrofitUrlManager.getInstance().setGlobalDomain("")
        }

        fun clearCache() {
            // TODO: 目前没有缓存，没法清理，没有测试过
            var size = CacheDiskUtils.getInstance().cacheSize
            AlertDialog.Builder(mActivity).apply {
                setTitle("清除缓存")
                setCancelable(true)
                setMessage("缓存大小:${size}M")
                setNegativeButton("确定") { _, _ -> CacheDiskUtils.getInstance().clear() }
                setPositiveButton("取消", null)
            }.show()
        }
    }

    //TODO 文件下载更新
    class DownloadBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val dwnId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                // TODO: 下载完成，安装程序
                LogUtils.e(dwnId)
            }
        }
    }
}