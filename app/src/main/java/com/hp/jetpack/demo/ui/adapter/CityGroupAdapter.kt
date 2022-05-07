package com.hp.jetpack.demo.ui.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.City
import com.hp.jetpack.demo.data.bean.CityGroupBean

class CityGroupAdapter(data: MutableList<City>) : BaseQuickAdapter<City,BaseViewHolder>(R.layout.item_city_group,data) {
    override fun convert(holder: BaseViewHolder, item: City) {
        holder.setText(R.id.tv_group,item.title)
        var rlvCity = holder.getView<RecyclerView>(R.id.rlv_city)
        rlvCity.adapter = CityAdapter(item.lists)
        rlvCity.layoutManager = GridLayoutManager(rlvCity.context,3)
    }
}