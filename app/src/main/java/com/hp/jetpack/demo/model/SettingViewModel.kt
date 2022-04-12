package com.hp.jetpack.demo.model

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.result.EnterpriseBean
import com.hp.jetpack.demo.data.state.MyResultState
import com.hp.jetpack.demo.ext.request
import com.hp.jetpack.demo.ext.request2
import com.hp.jetpack.demo.network.apiService
import kotlinx.coroutines.delay

class SettingViewModel : BaseViewModel() {

    var enterpriseBeanList = MutableLiveData<MyResultState<List<EnterpriseBean>>>()

    var showDialogTag = MutableLiveData<Boolean>()

    fun getEnterprise() {
        request2({

            apiService.getEnterprise()
        }, {
            LogUtils.e("success")
            enterpriseBeanList.postValue(MyResultState(0, "成功", it))
        }, {
            LogUtils.e("error")
            //TODO 错误处理
            var message: String = it.message ?: "未知错误"
            enterpriseBeanList.postValue(MyResultState(1, message, listOf()))
        }, true)
    }
}