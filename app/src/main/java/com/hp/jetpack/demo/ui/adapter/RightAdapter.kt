package com.hp.jetpack.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.City

class RightAdapter(data: MutableList<City>) :
    BaseQuickAdapter<City, BaseViewHolder>(R.layout.item_code, data) {
    override fun convert(holder: BaseViewHolder, item: City) {
        holder.setText(R.id.tv, item.title)
    }
}