package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.WxArticleTree
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class WxArticleViewModel : BaseViewModel() {
    var articleTreeState = UnPeekLiveData<ResultState2<ArrayList<WxArticleTree>>>()
    fun getWxArticleTree() {
        request3({ apiService.getWxArticleTree()},articleTreeState)
    }
}