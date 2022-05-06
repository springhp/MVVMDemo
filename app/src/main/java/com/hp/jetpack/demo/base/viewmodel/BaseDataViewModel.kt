package com.hp.jetpack.demo.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hp.jetpack.demo.network.exception.AppException
import com.kunminx.architecture.ui.callback.UnPeekLiveData

open class BaseDataViewModel : BaseViewModel() {

    var error: MutableLiveData<AppException> = MutableLiveData()

}