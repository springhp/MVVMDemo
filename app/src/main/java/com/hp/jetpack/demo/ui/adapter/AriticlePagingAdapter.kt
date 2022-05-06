package com.hp.jetpack.demo.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.AriticleResponse

val USER_COMPARATOR = object : DiffUtil.ItemCallback<AriticleResponse>() {
    override fun areItemsTheSame(oldItem: AriticleResponse, newItem: AriticleResponse): Boolean =
        // User ID serves as unique ID
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AriticleResponse, newItem: AriticleResponse): Boolean =
        // Compare full contents (note: Java users should call .equals())
        oldItem == newItem
}
class AriticlePagingAdapter : PagingDataAdapter<AriticleResponse,BaseViewHolder>(USER_COMPARATOR) {


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        var ariticle = getItem(position)
        ariticle?.let {
            holder.setText(R.id.item_home_author,it.author)
            LogUtils.e("-------",it.chapterName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(parent.getItemView(R.layout.item_ariticle))
    }
}