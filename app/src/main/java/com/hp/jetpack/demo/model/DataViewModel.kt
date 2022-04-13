package com.hp.jetpack.demo.model

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.model.databind.StringObservableField
import com.hp.jetpack.demo.network.apiService
import kotlinx.coroutines.launch
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.MediaType
import okhttp3.RequestBody

class DataViewModel : BaseViewModel() {

    var logs = StringObservableField()

    fun loadData(data: String) {
        viewModelScope.launch {
            runCatching {
                // 获取待下发的设备和数据
                var distributeDataResult = apiService.getData(data)
                if (distributeDataResult.isSuccess() && distributeDataResult.data.isNotEmpty()) {
                    var log = "${distributeDataResult.data.size}条数据待下发"
                    updateLogs(log)

                    distributeDataResult.data.forEach { distributeData ->
                        var data = mapOf("urlPath" to distributeData.register_base64)
                        // 获取base64的图片
                        var imageResult = apiService.getBase64Image(GsonUtils.toJson(data))
                        if (imageResult.isSuccess()) {
                            distributeData.register_base64 = imageResult.info
                            var data = mapOf(
                                "password" to "123456",
                                "data" to GsonUtils.toJson(distributeData)
                            )
                            var showMsg = "成功"
                            var state = "1"
                            var body = RequestBody.create(JSON, GsonUtils.toJson(data))
                            runCatching {
                                // 下发数据到设备
                                // TODO: 需要测试URL是否正确
                                RetrofitUrlManager.getInstance()
                                    .putDomain("DeviceWhiteUrl", distributeData.url);
                                var addDeviceWhiteResult = apiService.addDeviceWhite(body)
                                showMsg = addDeviceWhiteResult.message
                                if (addDeviceWhiteResult.result != 0) {
                                    state = "2"
                                }
                            }.onFailure {
                                showMsg = "设备不在线"
                                state = "2"
                            }
                            // 告诉服务器是否成功
                            var dd = mapOf(
                                "deviceId" to distributeData.sendtype,
                                "state" to state,
                                "resultStr" to showMsg,
                                "sendDate" to TimeUtils.millis2String(
                                    System.currentTimeMillis(), "yyyy-MM-dd"
                                ),
                                "sendType" to distributeData.sendtype,
                            )
                            apiService.updateData(GsonUtils.toJson(dd))
                        } else {
                            var log = "${distributeData.name}-下发失败"
                            updateLogs(log)
                        }
                    }
                } else {
                    var log = "0条数据待下发"
                    updateLogs(log)
                }
            }.onSuccess {

            }.onFailure {
                var errorMsg = it.message ?: "未知错误"
                updateLogs(errorMsg)
            }
        }
    }

    private fun updateLogs(log: String) {
        LogUtils.e(log)
        var log = "${logs.get()}\n $log"
        logs.set(log)
    }

    val JSON = MediaType.parse("application/json; charset=utf-8")
}