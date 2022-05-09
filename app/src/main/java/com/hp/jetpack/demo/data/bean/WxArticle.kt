package com.hp.jetpack.demo.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WxArticleTree(
    var name: String,
    var id: Int
) : Parcelable
