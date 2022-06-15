package com.batofgotham.moviereviews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.batofgotham.moviereviews.data.model.TvShows
import com.batofgotham.moviereviews.data.remote.movies.ApiService

class TvShowsSearchPagingSource(
    private val apiService: ApiService,
    private val search: String
) : PagingSource<Int, TvShows>() {
    override fun getRefreshKey(state: PagingState<Int, TvShows>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShows> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getSearchTvShows(search, position)

            return LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}