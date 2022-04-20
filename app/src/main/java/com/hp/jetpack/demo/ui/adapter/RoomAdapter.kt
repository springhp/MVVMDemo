package com.hp.jetpack.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.database.entity.User

class RoomAdapter(data:MutableList<User>) :BaseQuickAdapter<User,BaseViewHolder>(R.layout.item_room,data) {

    override fun convert(holder: BaseViewHolder, item: User) {
        holder.setText(R.id.tv_id,"${item.id}")
        holder.setText(R.id.tv_name,item.name?:"")
        holder.setText(R.id.tv_password,item.password?:"")
    }
}