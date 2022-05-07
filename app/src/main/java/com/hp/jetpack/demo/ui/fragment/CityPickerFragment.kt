package com.hp.jetpack.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.City
import com.hp.jetpack.demo.data.bean.Country
import com.hp.jetpack.demo.databinding.FragmentCityPickerBinding
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.nav
import com.hp.jetpack.demo.ui.adapter.CityGroupAdapter
import com.hp.jetpack.demo.ui.adapter.RightAdapter
import java.io.BufferedReader
import java.io.InputStreamReader

class CityPickerFragment : BaseFragment<BaseViewModel, FragmentCityPickerBinding>() {

    private lateinit var mRightAdapter: RightAdapter
    private lateinit var mCityGroupAdapter: CityGroupAdapter

    private lateinit var mLayoutManager: LinearLayoutManager
    private var mData: MutableList<City> = mutableListOf()

    override fun layoutId(): Int = R.layout.fragment_city_picker

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.smartRefreshLayout.setEnableRefresh(false)
        mDataBinding.smartRefreshLayout.setEnableLoadMore(false)
        mDataBinding.toolbar.initClose(titleStr = "城市选择") {
            nav().navigateUp()
        }
        mRightAdapter = RightAdapter(mData)
        mDataBinding.rlvRight.apply {
            adapter = mRightAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        mRightAdapter.setOnItemClickListener { _, _, position ->
            var code = mData[position].title
            ToastUtils.showLong(code)
            context?.let {
                var scroller = TopSmoothScroller(it)
                scroller.targetPosition = position
                mLayoutManager.startSmoothScroll(scroller)
            }
        }
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mCityGroupAdapter = CityGroupAdapter(mData)
        mDataBinding.rlvCity.apply {
            adapter = mCityGroupAdapter
            layoutManager = mLayoutManager
        }

        context?.apply {
            runCatching {
                var bufferedReader =
                    BufferedReader(InputStreamReader(this.assets.open("city.json")))
                var line: String?
                var stringBuffer = StringBuffer()
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuffer.append(line)
                }
                stringBuffer.toString()
            }.onSuccess {
                var country = GsonUtils.fromJson(it, Country::class.java)
                mData.addAll(country.city)
                mCityGroupAdapter.notifyDataSetChanged()
                mRightAdapter.notifyDataSetChanged()
            }.onFailure {
                LogUtils.e("读取文件失败")
            }
        }
    }

    class TopSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }

}