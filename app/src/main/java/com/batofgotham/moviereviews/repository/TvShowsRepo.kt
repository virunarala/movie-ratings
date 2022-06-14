package com.batofgotham.moviereviews.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import com.batofgotham.moviereviews.paging.TvShowsPagingSource
import com.batofgotham.moviereviews.paging.TvShowsSearchPagingSource
import javax.inject.Inject

class TvShowsRepo @Inject constructor(private val apiService: ApiService) {

//    suspend fun getTvShowsFromNetwork(): List<TvShows> {
//        val result = apiService.getTopRatedTvShows(1).results
//        Log.i("Tv Repository", result.toString())
//        return result
//    }

    fun getTvShowsFromNetwork() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { TvShowsPagingSource(apiService) }
    ).liveData

    fun getTvShowsSearch(search: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { TvShowsSearchPagingSource(apiService, search) }
    ).liveData


}