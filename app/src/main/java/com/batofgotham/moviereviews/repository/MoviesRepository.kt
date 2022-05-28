package com.batofgotham.moviereviews.repository

import android.util.Log
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.remote.movies.MoviesApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApiService: MoviesApiService) {

    suspend fun getMoviesFromNetwork(): List<Movie>{
        val result = moviesApiService.getPopularMovies().results
        Log.i("Repository",result.toString())
        return result
    }

}