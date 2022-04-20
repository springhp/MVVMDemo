package com.hp.jetpack.demo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.model.databind.StringObservableField
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class LoginViewModel() : BaseViewModel() {
    val nameField = StringObservableField("hp")
    val passwordField = StringObservableField("123456")
    val loginState = MutableLiveData<Boolean>()

    fun onNameChanged(name: CharSequence) {
        nameField.set(name.toString())
    }

    fun onPasswordChanged(password: CharSequence) {
        passwordField.set(password.toString())
    }

    fun login(name: String, password: String) {
        //        ws://data.90vs.com:8181/ws/
        //data.90vs.com

        viewModelScope.launch {
            runCatching {
                Thread{
                    var socket = Socket("data.90vs.com",8181)
                    var outputStream = socket.getOutputStream()
                    val bufferedReaderIn: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))

                    LogUtils.e(socket.isConnected)
                    LogUtils.e(bufferedReaderIn.read())
                }.start()
            }.onSuccess {

            }.onFailure {
                LogUtils.e(it.message)
            }
        }
//        request2({
//                 apiService.login(name,password)
//        },{
//            loginState.postValue(true)
//        },{
//            loginState.postValue(false)
//        })
//        request({
//            apiService.login(name, password)
//        }, loginState, true, "登陆中...")
    }
}