package com.hp.jetpack.demo.data.state

data class MyResultState<T>(var code: Int, var message: String, var data: T) {
    fun isSuccess() = code == 0

    fun getResponseData() = data

    fun getResponseCode() = code

    fun getResponseMsg() = message
}