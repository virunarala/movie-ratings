package com.batofgotham.moviereviews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batofgotham.moviereviews.utils.Constants
import com.squareup.moshi.Json


data class MovieRemote(

    @Json(name="poster_path")
    val posterPath: String? = null,

    val adult: Boolean?,

    val overview:String?,

    @Json(name="release_date")
    val releaseDate:String?,

    @Json(name="genre_ids")
    val genreIds: List<Int>?,

    @PrimaryKey
    val id: Int,

    @Json(name="original_title")
    val originalTitle: String?,

    @Json(name="original_language")
    val originalLanguage: String?,

    val title: String?,

    @Json(name="backdrop_path")
    val backdropPath: String? = null,

    val popularity: Double?,

    @Json(name="vote_count")
    val voteCount: Int?,

    val video: Boolean?,

    @Json(name="vote_average")
    val voteAverage: Double?
)