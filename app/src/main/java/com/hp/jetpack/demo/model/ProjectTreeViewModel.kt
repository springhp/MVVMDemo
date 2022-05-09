package com.hp.jetpack.demo.model

import com.hp.jetpack.demo.base.viewmodel.BaseViewModel
import com.hp.jetpack.demo.data.bean.ProjectTree
import com.hp.jetpack.demo.data.state.ResultState2
import com.hp.jetpack.demo.ext.request3
import com.hp.jetpack.demo.network.apiService
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class ProjectTreeViewModel : BaseViewModel() {
    var projectTreeState = UnPeekLiveData<ResultState2<ArrayList<ProjectTree>>>()
    fun getProjectTree() {
        request3({ apiService.getProjectTree()},projectTreeState)
    }
}