package com.hp.jetpack.demo.ui.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.ProjectResponse

class ProjectChildAdapter(data: MutableList<ProjectResponse>) :
    BaseQuickAdapter<ProjectResponse, BaseViewHolder>(R.layout.item_project_child, data) {

    override fun convert(holder: BaseViewHolder, item: ProjectResponse) {
        var view = holder.getView<AppCompatImageView>(R.id.iv)
        Glide.with(view).load(item.envelopePic).centerCrop().into(view)
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_subtitle, item.desc)
        holder.setText(R.id.tv_author, item.author)
        holder.setText(R.id.tv_time, item.niceDate)
    }
}