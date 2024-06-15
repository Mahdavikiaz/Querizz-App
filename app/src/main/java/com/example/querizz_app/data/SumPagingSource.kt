package com.example.querizz_app.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.querizz_app.data.api.service.ApiService
import com.example.querizz_app.data.response.DataItem

class SumPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, DataItem>() {

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            // Menggunakan token dalam permintaan API
            val responseData = apiService.getSummaries(position, params.loadSize)
            LoadResult.Page(
                data = responseData.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}