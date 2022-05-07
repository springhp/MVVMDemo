package com.hp.jetpack.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R

class CityAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_city, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.btn, item)
    }
}