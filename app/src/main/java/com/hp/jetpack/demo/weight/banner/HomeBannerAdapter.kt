package com.hp.jetpack.demo.weight.banner

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:
 */

import android.view.View
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.data.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter

class HomeBannerAdapter : BaseBannerAdapter<BannerResponse, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemhome
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }


}
