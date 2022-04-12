package com.hp.jetpack.demo.model

import androidx.lifecycle.MutableLiveData
import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.UserInfo
import com.hp.jetpack.demo.ext.request
import com.hp.jetpack.demo.model.databind.StringObservableField
import com.hp.jetpack.demo.network.apiService
import com.hp.jetpack.demo.network.state.ResultState
import com.hp.jetpack.demo.util.LogUtils

class LoginViewModel() : BaseViewModel() {
    val nameField = StringObservableField("hp")
    val passwordField = StringObservableField("123456")
    val loginState = MutableLiveData<ResultState<UserInfo>>()

    fun onNameChanged(name: CharSequence) {
        nameField.set(name.toString())
    }

    fun onPasswordChanged(password: CharSequence) {
        passwordField.set(password.toString())
    }

    fun login(name: String, password: String) {
        com.blankj.utilcode.util.LogUtils.e("login")
        request({
            apiService.login(name, password)
        }, loginState, true, "登陆中...")
    }
}