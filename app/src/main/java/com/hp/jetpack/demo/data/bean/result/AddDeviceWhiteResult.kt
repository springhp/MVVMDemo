package com.hp.jetpack.demo.data.bean.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AddDeviceWhiteResult(
    val result: Int, val message: String
    ) : Parcelable