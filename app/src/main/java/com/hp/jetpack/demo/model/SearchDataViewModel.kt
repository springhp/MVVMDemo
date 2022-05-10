package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.ApiPagerResponse
import com.hp.jetpack.demo.data.bean.AriticleResponse
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class SearchDataViewModel() : BaseViewModel() {
    var page = 0

    val searchState = UnPeekLiveData<ResultState2<ApiPagerResponse<ArrayList<AriticleResponse>>>>()

    fun getSearchData(flag: Boolean, key: String) {
        if (flag) {
            page = 0
        }
        request3({ apiService.getSearchDataByKey(page, key) }, searchState, {
            page++
        }, false)
    }
}