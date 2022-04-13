package com.hp.jetpack.demo.data.bean.result

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class AddDeviceWhiteResult(
    val result: Int, val message: String
    ) : Parcelable