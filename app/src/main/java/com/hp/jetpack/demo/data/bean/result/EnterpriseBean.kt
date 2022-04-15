package com.hp.jetpack.demo.data.bean.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EnterpriseBean(
    var id: String = "",
    var text: String = ""
) : Parcelable