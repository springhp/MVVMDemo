package com.hp.jetpack.demo.ui.activity

import android.content.Intent
import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.MainActivity
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseActivity
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.ActivityLauncherBinding
import com.hp.jetpack.demo.ext.gone
import com.hp.jetpack.demo.ext.visible
import com.hp.jetpack.demo.ui.adapter.LauncherBannerAdapter
import com.hp.jetpack.demo.util.CacheUtil
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

class LauncherActivity : BaseActivity<BaseViewModel, ActivityLauncherBinding>() {
    override fun layoutId(): Int = R.layout.activity_launcher

    private var resList = mutableListOf("唱", "跳", "r a p")

    override fun createObserver() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
        }
        mDataBinding.click = ProxyClick()

        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }
        if (CacheUtil.isFirst()) {
            CacheUtil.setFirst(false)
            mDataBinding.welcomeImage.gone()
            mDataBinding.banner.apply {
                val launcherBannerAdapter = LauncherBannerAdapter(resList)
                setAdapter(launcherBannerAdapter, false)
                addBannerLifecycleObserver(this@LauncherActivity)
                indicator = CircleIndicator(this@LauncherActivity)
                isAutoLoop(false)

                addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        if (position == resList.size - 1) {
                            mDataBinding.btnOk.visible()
                        } else {
                            mDataBinding.btnOk.gone()
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }
                })
            }
        } else {
            mDataBinding.welcomeImage.visible()
            //
            mDataBinding.welcomeImage.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 300)
        }
    }

    inner class ProxyClick {
        fun gotoMain() {
            startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
            finish()
        }
    }
}