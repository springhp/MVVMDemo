package com.hp.jetpack.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hp.jetpack.demo.R
import com.youth.banner.adapter.BannerAdapter


class LauncherBannerAdapter(dataList: MutableList<String>?) : BannerAdapter<String, LauncherBannerAdapter.ViewHolder>(
    dataList
) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): LauncherBannerAdapter.ViewHolder {
        var view  = LayoutInflater.from(parent.context).inflate(R.layout.banner_launcher,parent,false)
        return ViewHolder(view)
    }

    override fun onBindView(
        holder: LauncherBannerAdapter.ViewHolder?,
        data: String?,
        position: Int,
        size: Int
    ) {
        holder?.tvMessage?.text = data
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvMessage: TextView = view.findViewById(R.id.tv_message)
    }
}