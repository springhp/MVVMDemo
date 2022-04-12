package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.model.databind.StringObservableField

class VisitorViewModel : BaseViewModel() {

    var name = StringObservableField()

    var phone = StringObservableField()

    var id = StringObservableField()

}