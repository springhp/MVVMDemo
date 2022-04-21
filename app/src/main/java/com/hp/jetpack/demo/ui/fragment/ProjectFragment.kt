package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.databinding.FragmentProjectBinding
import com.hp.jetpack.demo.ext.bindViewPager2
import com.hp.jetpack.demo.ext.init


class ProjectFragment : BaseFragment<BaseViewModel, FragmentProjectBinding>() {
    override fun layoutId() = R.layout.fragment_project

    private val fragments = mutableListOf<Fragment>()
    private val mDataList = mutableListOf("关注", "推荐", "最新")
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.viewpage.init(this, fragments)
        mDataBinding.magicIndicator.bindViewPager2(mDataBinding.viewpage, mDataList)

        mDataList.forEachIndexed { index, _ ->
            fragments.add(TestFragment.newInstance("$index"))
        }
        mDataBinding.magicIndicator.navigator.notifyDataSetChanged()
        mDataBinding.viewpage.adapter?.notifyDataSetChanged()
        mDataBinding.viewpage.offscreenPageLimit = fragments.size

    }


}