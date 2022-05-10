package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.SearchResponse
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.hp.jetpack.demo.util.CacheUtil
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class SearchViewModel() : BaseViewModel() {
    val searchState = UnPeekLiveData<ResultState2<ArrayList<SearchResponse>>>()

    val historyState = UnPeekLiveData<ArrayList<String>>()

    fun getSearchData() {
        request3({ apiService.getSearchData() }, searchState)
    }

    fun getSearchHistoryData(){
        historyState.postValue(CacheUtil.getSearchHistoryData())
    }
}