package com.batofgotham.moviereviews.data.model

import com.squareup.moshi.Json

data class MovieNetworkResponse(
    val page: Int,
    val results: List<MovieRemote>,

    @Json(name="total_results")
    val totalResults: Int,

    @Json(name="total_pages")
    val totalPages: Int
)
