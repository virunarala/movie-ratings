package com.batofgotham.moviereviews.repository

import com.batofgotham.moviereviews.data.model.Configuration
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class ConfigRepo @Inject constructor(private val apiService: ApiService) {

    suspend fun getApiConfig(): Configuration{
        return apiService.getApiConfig()
    }
}