package id.hadi.dicoding.storyapp.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Created by nurrahmanhaadii on 13,May,2024
 */
class FakePagingSource<T: Any>(private val data: List<T>) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPageNumber = params.key ?: 0
            val nextPageData = data.drop(nextPageNumber * params.loadSize).take(params.loadSize)
            LoadResult.Page(
                data = nextPageData,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageData.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int {
        return 0
    }
}