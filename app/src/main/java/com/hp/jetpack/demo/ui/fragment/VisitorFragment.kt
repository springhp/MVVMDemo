package com.hp.jetpack.demo.ui.fragment

import android.os.Bundle
import com.bumptech.glide.Glide
import com.hp.jetpack.demo.R
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.databinding.FragmentVisitorBinding
import com.hp.jetpack.demo.model.VisitorViewModel
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo

class VisitorFragment : BaseFragment<VisitorViewModel, FragmentVisitorBinding>() {
    override fun layoutId(): Int = R.layout.fragment_visitor

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.model = mViewModel

        mDataBinding.iv.setOnClickListener {

            EasyPhotos.createCamera(this@VisitorFragment, true)//参数说明：上下文
                .setFileProviderAuthority("com.hp.jetpack.demo.fileprovider")
                .start(object : SelectCallback() {
                    override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                        photos?.let {
                            Glide.with(this@VisitorFragment).load(it[0].uri).into(mDataBinding.iv)
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }
    }

    override fun initData() {
        super.initData()

    }

}