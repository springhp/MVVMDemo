package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentProjectChlidBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.navigateAction
import com.hp.jetpack.demo.model.ProjectViewModel
import com.hp.jetpack.demo.model.WxArticleViewModel
import com.hp.jetpack.demo.ui.adapter.AriticleAdapter
import com.hp.jetpack.demo.ui.adapter.ProjectChildAdapter
import com.hp.jetpack.demo.weight.recyclerview.SpaceItemDecoration


class WxArticleChildFragment : BaseFragment<WxArticleViewModel, FragmentProjectChlidBinding>() {
    var cid: Int = 0
    lateinit var mAdapter: AriticleAdapter
    override fun layoutId() = R.layout.fragment_project_chlid

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            cid = it.getInt("id")
        }
        mAdapter = AriticleAdapter(mutableListOf(),true)
        mDataBinding.rlv.apply {
            adapter = mAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
        }
        mDataBinding.smartRefreshLayout.apply {
            setOnRefreshListener {
                mViewModel.getWxArticleList(true, cid)
            }
            setOnLoadMoreListener {
                mViewModel.getWxArticleList(false, cid)
            }
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putParcelable(
                    "ariticleData",
                    mAdapter.data[position]
                    //1为head
                )
            })
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getWxArticleList(true, cid)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.articleListState.observe(viewLifecycleOwner) {
            mDataBinding.smartRefreshLayout.finishRefresh()
            mDataBinding.smartRefreshLayout.finishLoadMore()
            if (it.isSuccess()) {
                it.data?.let { data -> mAdapter.addData(data.datas) }
            }
        }
    }

    companion object {
        fun newInstance(id: Int): WxArticleChildFragment {
            val args = Bundle()

            val fragment = WxArticleChildFragment()
            fragment.arguments = args
            args.putInt("id", id)
            return fragment
        }
    }
}