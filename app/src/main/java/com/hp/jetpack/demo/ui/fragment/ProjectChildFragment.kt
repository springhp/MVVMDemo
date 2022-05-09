package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentProjectChlidBinding
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.navigateAction
import com.hp.jetpack.demo.model.ProjectViewModel
import com.hp.jetpack.demo.ui.adapter.ProjectChildAdapter


class ProjectChildFragment : BaseFragment<ProjectViewModel, FragmentProjectChlidBinding>() {
    var cid: Int = 0
    lateinit var mAdapter: ProjectChildAdapter
    override fun layoutId() = R.layout.fragment_project_chlid

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            cid = it.getInt("cid")
        }
        mAdapter = ProjectChildAdapter(mutableListOf())
        mDataBinding.rlv.adapter = mAdapter
        mDataBinding.rlv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mDataBinding.smartRefreshLayout.apply {
            setOnRefreshListener {
                mViewModel.getProjectList(true, cid)
            }
            setOnLoadMoreListener {
                mViewModel.getProjectList(false, cid)
            }
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putParcelable(
                    "project",
                    mAdapter.data[position]
                    //1为head
                )
            })
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getProjectList(true, cid)
    }
    //保存到数据库

    override fun createObserver() {
        super.createObserver()
        mViewModel.projectListState.observe(viewLifecycleOwner) {
            mDataBinding.smartRefreshLayout.finishRefresh()
            mDataBinding.smartRefreshLayout.finishLoadMore()
            if (it.isSuccess()) {
                it.data?.let { data -> mAdapter.addData(data.datas) }
            }
        }
    }

    companion object {
        fun newInstance(cid: Int): ProjectChildFragment {
            val args = Bundle()

            val fragment = ProjectChildFragment()
            fragment.arguments = args
            args.putInt("cid", cid)
            return fragment
        }
    }
}