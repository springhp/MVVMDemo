package com.hp.jetpack.demo.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 文章的标签
 */
@Parcelize
data class CityBean(
    var city: String,
    var code: String,
) : Parcelable
