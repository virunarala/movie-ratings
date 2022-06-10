package com.batofgotham.moviereviews.repository

import android.util.Log
import com.batofgotham.moviereviews.data.database.MovieDao
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val apiService: ApiService,private val movieDao: MovieDao) {

    private val TAG = MovieRepo::class.java.simpleName

    private suspend fun getMoviesFromNetwork(): List<Movie>{
        val result = apiService.getPopularMovies().results
        Log.i(TAG,result.toString())
        return result
    }

    private suspend fun getMoviesFromDb(): List<Movie>{
        val result = movieDao.getAllMovies()
        Log.i(TAG,result.toString())
        return result
    }

    private suspend fun addMoviesToDb(movies: List<Movie>): List<Long> {
        val rowIds = movieDao.insertMovies(movies)
        return rowIds
    }

    suspend fun getMovies(): List<Movie>{
        /**
         * Try to fetch from db
         */
        var resultFromDb = movieDao.getAllMovies()

        /**
         * If data not available in db, fetch from internet, cache it in db and return the result
         */
        if(resultFromDb.isEmpty()){
            val resultFromNetwork = apiService.getPopularMovies()
            addMoviesToDb(resultFromNetwork.results)
            resultFromDb = movieDao.getAllMovies()
        }

        return resultFromDb
    }
}