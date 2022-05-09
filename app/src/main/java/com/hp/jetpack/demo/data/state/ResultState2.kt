package com.hp.jetpack.demo.data.state

data class ResultState2<T>(
    var code: Int,
    var message: String = "",
    var data: T? = null
) {
    fun isSuccess() = code == 0
}
