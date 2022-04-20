package com.hp.jetpack.demo.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseActivity
import com.hp.jetpack.demo.databinding.ActivityRoomBinding
import com.hp.jetpack.demo.ext.initClose
import com.hp.jetpack.demo.ext.showMessage
import com.hp.jetpack.demo.model.database.RoomViewModel
import com.hp.jetpack.demo.ui.adapter.RoomAdapter

class RoomActivity : BaseActivity<RoomViewModel, ActivityRoomBinding>() {
    lateinit var mAdapter:RoomAdapter

    override fun layoutId(): Int = R.layout.activity_room

    override fun createObserver() {
        mViewModel.addFlag.observe(this) {
            var message = if (it == true) "添加成功" else "添加失败"
            showMessage(message)
        }

        mViewModel.deleteFlag.observe(this) {
            var message = if (it == true) "删除成功" else "删除失败"
            showMessage(message)
        }

        mViewModel.data.observe(this){
            mAdapter.addData(it)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.toolbar.apply {
            inflateMenu(R.menu.room_menu)
            setOnMenuItemClickListener() {
                when (it.itemId) {
                    R.id.room_add -> {
                        mViewModel.addUser("hp${System.currentTimeMillis()}", "123456")
                    }
                    R.id.room_delete ->{
                        mViewModel.deleteUser(mAdapter.data[0])
                    }
                }
                true
            }
        }.initClose(titleStr = "Room", backImg = R.drawable.ic_back) {
            finish()
        }

        mAdapter = RoomAdapter(mutableListOf())
        mDataBinding.rlvUser.apply {
            adapter = mAdapter
            layoutManager =
                LinearLayoutManager(this@RoomActivity, LinearLayoutManager.VERTICAL, false)
        }

        mViewModel.getAll()
    }
}