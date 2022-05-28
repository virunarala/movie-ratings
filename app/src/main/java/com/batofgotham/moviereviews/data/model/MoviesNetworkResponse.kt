package com.batofgotham.moviereviews.data.model

import com.batofgotham.moviereviews.data.model.Movie
import com.squareup.moshi.Json

data class MoviesNetworkResponse(
    val page: Int,
    val results: List<Movie>,

    @Json(name="total_results")
    val totalResults: Int,

    @Json(name="total_pages")
    val totalPages: Int
)
