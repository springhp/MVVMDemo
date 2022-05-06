package com.hp.jetpack.demo.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blankj.utilcode.util.LogUtils
import com.hp.jetpack.demo.data.bean.AriticleResponse
import com.hp.jetpack.demo.network.ApiService

class TestDataSource(private val service: ApiService) : PagingSource<Int, AriticleResponse>() {
    override fun getRefreshKey(state: PagingState<Int, AriticleResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AriticleResponse> {
        return try {
            val pageNumber = params.key ?: 0
            val response = service.getAritrilList(pageNumber)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            var nextKey = if (!response.data.isEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response.data.datas,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}