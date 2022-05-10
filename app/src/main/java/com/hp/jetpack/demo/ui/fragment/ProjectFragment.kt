package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.ProjectTree
import com.hp.jetpack.demo.databinding.FragmentProjectBinding
import com.hp.jetpack.demo.ext.bindViewPager2
import com.hp.jetpack.demo.ext.init
import com.hp.jetpack.demo.model.ProjectTreeViewModel


class ProjectFragment : BaseFragment<ProjectTreeViewModel, FragmentProjectBinding>() {
    override fun layoutId() = R.layout.fragment_project

    private val fragments = mutableListOf<Fragment>()
    private val mDataList = mutableListOf<ProjectTree>()
    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.viewpage.init(this, fragments)
        mDataBinding.magicIndicator.bindViewPager2(mDataBinding.viewpage, mDataList)
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getProjectTree()
    }
    //保存到数据库

    override fun createObserver() {
        super.createObserver()
        mViewModel.projectTreeState.observe(viewLifecycleOwner) {
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