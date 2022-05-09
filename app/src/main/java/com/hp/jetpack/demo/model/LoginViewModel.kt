package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.UserInfo
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.model.databind.StringObservableField
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class LoginViewModel() : BaseViewModel() {
    val nameField = StringObservableField("hp8952")
    val passwordField = StringObservableField("123456")
    val loginState = UnPeekLiveData<ResultState2<UserInfo>>()

    fun onNameChanged(name: CharSequence) {
        nameField.set(name.toString())
    }

    fun onPasswordChanged(password: CharSequence) {
        passwordField.set(password.toString())
    }

    fun login() {
        request3({ apiService.login(nameField.get(), passwordField.get()) }, loginState)
    }

    fun register() {
        request3(
            { apiService.register(nameField.get(), passwordField.get(), passwordField.get()) },
            loginState
        )
    }
}