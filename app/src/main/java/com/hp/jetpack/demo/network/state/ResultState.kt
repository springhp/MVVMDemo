package com.hp.jetpack.demo.network.state

import androidx.lifecycle.MutableLiveData
import com.hp.jetpack.demo.network.BaseResponse
import com.hp.jetpack.demo.network.exception.AppException
import com.hp.jetpack.demo.network.exception.ExceptionHandle
import java.lang.Exception

sealed class ResultState<out T> {
    companion object {
        fun <T> onAppSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage: String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
    }

    data class Loading(val loadingMessage: String) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
}

fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>) {
    this.value = when {
        result.isSuccess() -> {
            ResultState.onAppSuccess(result.getResponseData())
        }
        else -> {
            ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
        }
    }
}

fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    this.value = ResultState.onAppError(ExceptionHandle.handleException(e))
}