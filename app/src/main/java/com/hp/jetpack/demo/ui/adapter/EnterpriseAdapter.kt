package com.hp.jetpack.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.result.EnterpriseBean

class EnterpriseAdapter(data: MutableList<EnterpriseBean>) :
    BaseQuickAdapter<EnterpriseBean, BaseViewHolder>(R.layout.item_enterprise, data) {
    override fun convert(holder: BaseViewHolder, item: EnterpriseBean) {
        holder.setText(R.id.tvText, item.text)
    }
}