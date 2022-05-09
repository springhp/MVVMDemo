package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.ApiPagerResponse
import com.hp.jetpack.demo.data.bean.ProjectResponse
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class ProjectViewModel : BaseViewModel() {
    private var page = 0

    var projectListState =
        UnPeekLiveData<ResultState2<ApiPagerResponse<ArrayList<ProjectResponse>>>>()

    fun getProjectList(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            page = 0
        }
        request3({ apiService.getProjectList(page, cid) }, projectListState, {
            page++
        }, false)
    }
}