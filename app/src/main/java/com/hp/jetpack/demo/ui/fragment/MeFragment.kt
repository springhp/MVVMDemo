package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentMeBinding
import com.hp.jetpack.demo.ext.jumpByLogin
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.navigateAction
import com.hp.jetpack.demo.model.MeViewModel
import com.hp.jetpack.demo.util.CacheUtil

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {
    override fun layoutId() = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.click = ProxyClick()
        mDataBinding.vm = mViewModel
    }

    inner class ProxyClick {

        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }

        /** 收藏 */
        fun collect() {
            nav().jumpByLogin {
                it.navigateAction(R.id.smart_refresh_layout_fragment)
            }
        }

        /** 积分 */
        fun integral() {
//            nav().jumpByLogin {
//                it.navigateAction(R.id.action_mainfragment_to_integralFragment,
//                    Bundle().apply {
//                        putParcelable("rank", rank)
//                    }
//                )
//            }
        }

        /** 文章 */
        fun ariticle() {
//            nav().jumpByLogin {
//                it.navigateAction(R.id.action_mainfragment_to_ariticleFragment)
//            }
        }

        fun todo() {
//            nav().jumpByLogin {
//                it.navigateAction(R.id.action_mainfragment_to_todoListFragment)
//            }
        }

        /** 玩Android开源网站 */
        fun about() {
//            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
//                putParcelable(
//                    "bannerdata",
//                    BannerResponse(
//                        title = "玩Android网站",
//                        url = "https://www.wanandroid.com/"
//                    )
//                )
//            })
        }

        /** 加入我们 */
        fun join() {
//            joinQQGroup("9n4i5sHt4189d4DvbotKiCHy-5jZtD4D")
        }

        /** 设置*/
        fun setting() {
            nav().navigateAction(R.id.setting_Fragment)
        }

        /**demo*/
        fun demo() {
//            nav().navigateAction(R.id.action_to_demoFragment)
        }

    }
}