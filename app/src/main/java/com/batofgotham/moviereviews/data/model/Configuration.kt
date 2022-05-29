package com.batofgotham.moviereviews.data.model

import com.squareup.moshi.Json

//Recommended to cache this info and refresh it every few days
data class Configuration(
    val images: Image,

    @Json(name="change_keys")
    val changeKeys: List<String>
)