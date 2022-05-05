package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.database.entity.User
import com.hp.jetpack.demo.databinding.FragmentMineBinding
import com.hp.jetpack.demo.model.database.RoomViewModel

class MineFragment : BaseFragment<RoomViewModel, FragmentMineBinding>() {
    override fun layoutId(): Int = R.layout.fragment_mine
    lateinit var mAdapter: BaseQuickAdapter<User, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.rlv.apply {

            mAdapter = object : BaseQuickAdapter<User, BaseViewHolder>(
                R.layout.item_test,
                mutableListOf()
            ) {
                override fun convert(holder: BaseViewHolder, item: User) {
                    holder.setText(R.id.tv_message, item.name)
                }
            }
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.data.observe(this) {
            mAdapter.addData(it)
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getAll()
    }
}