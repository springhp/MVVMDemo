package com.hp.jetpack.demo.data.bean.result

import com.hp.jetpack.demo.network.BaseResponse

data class BaseResult<T>(val code: Int, val info: String, val data: T) :
    BaseResponse<T>() {
    override fun isSuccess() = code == 200

    override fun getResponseData() = data

    override fun getResponseCode() = code

    override fun getResponseMsg() = info
}