package com.batofgotham.moviereviews.data.model

data class TvNetworkResponse(
    val page: Int,
    val results: List<TvShows>,
    val total_pages: Int,
    val total_results: Int
)