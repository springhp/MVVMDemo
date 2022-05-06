package com.hp.jetpack.demo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.ext.request
import com.hp.jetpack.demo.model.databind.StringObservableField
import com.hp.jetpack.demo.network.apiService
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
//        request({apiService.login(name,password)},)
    }
}