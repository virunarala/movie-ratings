package com.batofgotham.moviereviews.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import com.batofgotham.moviereviews.data.paging.TvShowsPagingSource
import com.batofgotham.moviereviews.data.paging.TvShowsSearchPagingSource
import javax.inject.Inject

class TvShowsRepo @Inject constructor(private val apiService: ApiService) {

    fun getTvShowsFromNetwork() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { TvShowsPagingSource(apiService) }
    ).liveData

    fun getTvShowsSearch(search: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { TvShowsSearchPagingSource(apiService, search) }
    ).liveData


}