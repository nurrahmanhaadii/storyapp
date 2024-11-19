package id.haadii.dicoding.submission.core

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.haadii.dicoding.submission.core.network.api.ApiService
import id.haadii.dicoding.submission.core.network.response.StoryResponse

/**
 * Created by nurrahmanhaadii on 31,March,2024
 */
class StoryPagingSource(private val apiService: ApiService): PagingSource<Int, StoryResponse>() {
    override fun getRefreshKey(state: PagingState<Int, StoryResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStories(position, params.loadSize)
            val data = responseData.listStory ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}