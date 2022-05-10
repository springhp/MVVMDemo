package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentSearchDataBinding
import com.hp.jetpack.demo.ext.convertPageData
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ext.navigateAction
import com.hp.jetpack.demo.model.SearchDataViewModel
import com.hp.jetpack.demo.ui.adapter.AriticleAdapter

class SearchDataFragment : BaseFragment<SearchDataViewModel, FragmentSearchDataBinding>() {
    var isRefresh = true
    override fun layoutId(): Int = R.layout.fragment_search_data
    private lateinit var key: String
    lateinit var mAdapter: AriticleAdapter
    override fun initView(savedInstanceState: Bundle?) {
        key = arguments?.getString("key").toString()

        mDataBinding.toolbar.initClose(titleStr = key, onBack = {
            nav().navigateUp()
        })

        mAdapter = AriticleAdapter(mutableListOf())
        mDataBinding.rlv.adapter = mAdapter
        mDataBinding.rlv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mDataBinding.smartRefreshLayout.apply {
            setOnRefreshListener {
                isRefresh = true
                mViewModel.getSearchData(isRefresh, key)
            }
            setOnLoadMoreListener {
                isRefresh = false
                mViewModel.getSearchData(isRefresh, key)
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

    override fun createObserver() {
        super.createObserver()
        mViewModel.searchState.observe(viewLifecycleOwner) {
            convertPageData(mDataBinding.smartRefreshLayout, mAdapter, it,isRefresh)
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getSearchData(isRefresh, key)
    }
}