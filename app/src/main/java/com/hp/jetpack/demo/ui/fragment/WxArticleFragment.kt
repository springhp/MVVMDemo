package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.WxArticleTree
import com.hp.jetpack.demo.databinding.FragmentWxAriticleBinding
import com.hp.jetpack.demo.ext.bindViewPager3
import com.hp.jetpack.demo.ext.init
import com.hp.jetpack.demo.model.WxArticleViewModel

class WxArticleFragment : BaseFragment<WxArticleViewModel,FragmentWxAriticleBinding>() {
    override fun layoutId(): Int = R.layout.fragment_wx_ariticle

    private val fragments = mutableListOf<Fragment>()
    private val mDataList = mutableListOf<WxArticleTree>()
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.viewpage.init(this, fragments)
        mDataBinding.magicIndicator.bindViewPager3(mDataBinding.viewpage, mDataList)
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getWxArticleTree()
    }
    //保存到数据库

    override fun createObserver() {
        super.createObserver()
        mViewModel.articleTreeState.observe(viewLifecycleOwner) {
            if (it.isSuccess()) {
                it.data?.let { list ->
                    mDataList.addAll(list)
                    LogUtils.e(mDataList.size)
                    mDataList.forEachIndexed { index, _ ->
                        fragments.add(ProjectChildFragment.newInstance(mDataList[index].id))
                    }

                    mDataBinding.viewpage.adapter?.notifyDataSetChanged()
                    mDataBinding.magicIndicator.navigator.notifyDataSetChanged()
                    mDataBinding.viewpage.offscreenPageLimit = fragments.size
                }
            } else {
                //显示错误页面
                LogUtils.e(it.message)
            }
        }
    }
}