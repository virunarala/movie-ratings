package com.batofgotham.moviereviews.repository

import android.util.Log
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val apiService: ApiService) {

    suspend fun getMoviesFromNetwork(): List<Movie>{
        val result = apiService.getPopularMovies().results
        Log.i("Repository",result.toString())
        return result
    }
}