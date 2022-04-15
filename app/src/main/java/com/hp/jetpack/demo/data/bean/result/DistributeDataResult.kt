package com.hp.jetpack.demo.data.bean.result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DistributeDataResult(
    var faceuseranddeviceid: String = "",
    var usertype: String = "",
    var employee_number: String = "",
    var icno: String = "",
    var name: String = "",
    var sex: String = "",
    var nation: String = "",
    var idno: String = "",
    var peoplestartdate: String = "",
    var peopleenddate: String = "",
    var company: String = "",
    var department: String = "",
    var register_base64: String = "",
    var password: String = "",
    var url: String = "",
    var customname: String = "",
    var id: String = "",
    var sendstatus: String = "",
    var sendtype: String = ""
) : Parcelable