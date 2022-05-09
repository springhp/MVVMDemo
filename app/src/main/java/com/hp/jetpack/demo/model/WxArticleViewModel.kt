package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.ApiPagerResponse
import com.hp.jetpack.demo.data.bean.AriticleResponse
import com.hp.jetpack.demo.data.bean.ProjectResponse
import com.hp.jetpack.demo.data.bean.WxArticleTree
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class WxArticleViewModel : BaseViewModel() {
    var page = 0
    var articleTreeState = UnPeekLiveData<ResultState2<ArrayList<WxArticleTree>>>()

    var articleListState = UnPeekLiveData<ResultState2<ApiPagerResponse<ArrayList<AriticleResponse>>>>()

    fun getWxArticleTree() {
        request3({ apiService.getWxArticleTree()},articleTreeState)
    }

    fun getWxArticleList(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            page = 0
        }
        request3({ apiService.getWxArticleList(page, cid) }, articleListState, {
            page++
        }, false)
    }
}