package com.batofgotham.moviereviews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.batofgotham.moviereviews.data.database.MovieDao
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.model.MovieNetworkResponse
import com.batofgotham.moviereviews.data.paging.MoviePagingSource
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val apiService: ApiService,private val movieDao: MovieDao) {

    private val TAG = MovieRepo::class.java.simpleName

    private suspend fun getMoviesFromNetwork(): List<Movie>{
        val result = apiService.getMoviesApiResponse(5).results
        Log.i(TAG,result.toString())
        return result
    }

    suspend fun getMoviesNetworkResponse(key: Int): MovieNetworkResponse{
        return apiService.getMoviesApiResponse(key)
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

    fun loadMovies(): LiveData<PagingData<Movie>>{
        /**
         * Try to fetch from db
         */
//        var resultFromDb = getMoviesFromDb()
        var resultFromDb = listOf<Movie>()

        /**
         * Specifies the number of items to load in a single page
         */
        val pageSize = 30
        /**
         * If data not available in db, fetch from internet, cache it in db and return the result
         */
//        if(resultFromDb.isEmpty()){
//            val resultFromNetwork = getMoviesNetworkResponse(5)
//            addMoviesToDb(resultFromNetwork.results)
//            resultFromDb = movieDao.getAllMovies()
//        }
//
//        return resultFromDb

        return Pager(
            PagingConfig(pageSize, enablePlaceholders = true)) {
                MoviePagingSource(this)
            }.liveData

    }
}