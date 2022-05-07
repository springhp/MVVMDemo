package com.hp.jetpack.demo.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 文章的标签
 */
@Parcelize
data class CityGroupBean(
    var city: MutableList<CityBean>,
    var code: String,
) : Parcelable
