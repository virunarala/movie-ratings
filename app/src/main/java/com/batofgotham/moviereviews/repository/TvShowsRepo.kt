package com.batofgotham.moviereviews.repository

import android.util.Log
import com.batofgotham.moviereviews.data.model.TvShows
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class TvShowsRepo @Inject constructor(private val apiService: ApiService) {

    suspend fun getTvShowsFromNetwork(): List<TvShows> {
        val result = apiService.getTopRatedTvShows().results
        Log.i("Tv Repository", result.toString())
        return result
    }

    suspend fun searchMovies(search: String): List<TvShows> {
        return apiService.getSearchTvShows(search).results
    }

}