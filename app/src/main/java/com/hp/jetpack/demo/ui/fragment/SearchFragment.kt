package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.JustifyContent
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.data.bean.SearchResponse
import com.hp.jetpack.demo.databinding.FragmentSearchBinding
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.model.SearchViewModel
import com.hp.jetpack.demo.util.CacheUtil

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override fun layoutId(): Int = R.layout.fragment_search
    private lateinit var mHistoryAdapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        mActivity.setSupportActionBar(mDataBinding.toolbar)
        setHasOptionsMenu(true)
        mDataBinding.toolbar.initClose {
            nav().navigateUp()
        }
        mHistoryAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_history) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.tv_history, item)
                    holder.getView<ImageView>(R.id.iv_delete).setOnClickListener {
                        CacheUtil.removeSearchHistoryData(item)
                        refreshHistoryData()
                    }
                }
            }
        mHistoryAdapter.setOnItemClickListener { _, _, position ->
            var key = mHistoryAdapter.data[position]
            gotoSearchResultFragment(key)
        }
        mDataBinding.rlvHistory.layoutManager = LinearLayoutManager(context)
        mDataBinding.rlvHistory.adapter = mHistoryAdapter

        mDataBinding.tvClear.setOnClickListener {
            CacheUtil.clearSearchHistoryData()
            refreshHistoryData()
        }
        mDataBinding.flexboxLayout.apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }
        mViewModel.getSearchData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        var searchView = menu.findItem(R.id.search_search).actionView as SearchView
        searchView.apply {
            queryHint = "请输入搜索条件"
            maxWidth = Int.MAX_VALUE
            onActionViewExpanded()
            isSubmitButtonEnabled = true //右边是否展示搜索图标
            val field = javaClass.getDeclaredField("mGoButton")
            field.run {
                isAccessible = true
                val mGoButton = get(searchView) as ImageView
                mGoButton.setImageResource(R.drawable.ic_search)
            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        LogUtils.e("search 2")
                        gotoSearchResultFragment(it)
                        CacheUtil.setSearchHistoryData(it)
                        refreshHistoryData()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            clearFocus()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        // 获取本地数据
        refreshHistoryData()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.searchState.observe(viewLifecycleOwner) {
            LogUtils.e(it.data?.size)
            it.data?.forEach { bean ->
                addChild(bean)
            }
        }

        mViewModel.historyState.observe(viewLifecycleOwner) {
            it?.let {
                mHistoryAdapter.data.clear()
                mHistoryAdapter.addData(it)
            }
        }
    }

    private fun addChild(info: SearchResponse) {
        val view: View = layoutInflater.inflate(R.layout.item_text, null, false)
        val tv = view.findViewById<TextView>(R.id.tv_hot)
        tv.text = info.name
        tv.setOnClickListener { textView: View ->
            var key = (textView as TextView).text.toString()
            CacheUtil.setSearchHistoryData(key)
            gotoSearchResultFragment(key)
            refreshHistoryData()
            // TODO:  跳转到页面
        }
        mDataBinding.flexboxLayout.addView(view)
    }

    fun refreshHistoryData() {
        mViewModel.getSearchHistoryData()
    }

    fun gotoSearchResultFragment(key: String) {
        nav().navigate(R.id.search_data_fragment, Bundle().apply {
            putString("key", key)
        })
    }
}