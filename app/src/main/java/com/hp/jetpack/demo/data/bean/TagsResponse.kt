package com.hp.jetpack.demo.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 文章的标签
 */
@Parcelize
data class TagsResponse(var name:String, var url:String): Parcelable
