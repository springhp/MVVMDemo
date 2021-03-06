package com.hp.jetpack.demo.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hp.jetpack.demo.base.activity.BaseFragment
import com.hp.jetpack.demo.base.fragment.BaseVmFragment
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.ApiPagerResponse
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.network.BaseResponse
import com.hp.jetpack.demo.network.exception.AppException
import com.hp.jetpack.demo.network.exception.ExceptionHandle
import com.hp.jetpack.demo.network.state.ResultState
import com.hp.jetpack.demo.network.state.paresException
import com.hp.jetpack.demo.network.state.paresResult
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.*
import java.lang.reflect.ParameterizedType

fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onAppLoading(loadingMessage)
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.message?.loge()
            resultState.paresException(it)
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            delay(3000)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request2(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            success(it.getResponseData())
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            else -> {
                throw AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}

fun <T> BaseVmFragment<*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: ((message: String) -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            if (onLoading == null) {
                showLoading(resultState.loadingMessage)
            } else {
                onLoading.invoke(resultState.loadingMessage)
            }
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            onError?.run {
                this(resultState.error)
            }
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request3(
    block: suspend () -> BaseResponse<T>,
    success: (ResultState2<T>) -> Unit,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            // 特殊处理一下
            var msg = if (it.getResponseMsg() == "") "请求成功" else it.getResponseMsg()
            success(ResultState2(it.getResponseCode(), msg, it.getResponseData()))
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            var exception = ExceptionHandle.handleException(it)
            success(ResultState2(exception.errCode, exception.errorMsg))
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request3(
    block: suspend () -> BaseResponse<T>,
    success: UnPeekLiveData<ResultState2<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            // 特殊处理一下
            var msg = if (it.getResponseMsg() == "") "请求成功" else it.getResponseMsg()
            success.postValue(ResultState2(it.getResponseCode(), msg, it.getResponseData()))
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            var exception = ExceptionHandle.handleException(it)
            success.postValue(ResultState2(exception.errCode, exception.errorMsg))
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request3(
    block: suspend () -> BaseResponse<T>,
    success: UnPeekLiveData<ResultState2<T>>,
    pagePlus: () -> Unit,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            pagePlus()
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            // 特殊处理一下
            var msg = if (it.getResponseMsg() == "") "请求成功" else it.getResponseMsg()
            success.postValue(ResultState2(it.getResponseCode(), msg, it.getResponseData()))
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            var exception = ExceptionHandle.handleException(it)
            success.postValue(ResultState2(exception.errCode, exception.errorMsg))
        }
    }
}

fun <T> BaseFragment<*, *>.convertPageData(
    smartRefreshLayout: SmartRefreshLayout,
    adapter: BaseQuickAdapter<T, BaseViewHolder>,
    state: ResultState2<ApiPagerResponse<ArrayList<T>>>,
    isRefresh: Boolean
) {
    smartRefreshLayout.finishRefresh()
    smartRefreshLayout.finishLoadMore()
    if (state.isSuccess()) {
        state.data?.let {
            if (it.datas.size < 40) {
                smartRefreshLayout.setEnableLoadMore(false)
            }
            if (isRefresh) {
                adapter.data.clear()
            }
            adapter.data.addAll(it.datas)
            adapter.notifyDataSetChanged()
        }
    } else {
        ToastUtils.showShort(state.message)
    }

}

