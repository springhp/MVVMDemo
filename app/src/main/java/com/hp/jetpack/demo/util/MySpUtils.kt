package com.hp.jetpack.demo.util

import com.blankj.utilcode.util.SPUtils

object MySpUtils {
    var enterpriseID: String?
        get() = SPUtils.getInstance().getString("enterprise_id", "1")
        set(enterpriseId) {
            SPUtils.getInstance().put("enterprise_id", enterpriseId)
        }
    var enterpriseName: String?
        get() = SPUtils.getInstance().getString("enterprise_name", "广州市(神山)绿源环保科技有限公司")
        set(enterpriseName) {
            SPUtils.getInstance().put("enterprise_name", enterpriseName)
        }
}