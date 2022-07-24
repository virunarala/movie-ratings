package com.batofgotham.moviereviews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.batofgotham.moviereviews.data.database.MovieDao
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import com.batofgotham.moviereviews.data.model.MovieNetworkResponse
import com.batofgotham.moviereviews.data.paging.MovieRemoteMediator
import com.batofgotham.moviereviews.data.remote.movies.ApiService
import javax.inject.Inject

class MovieRepo @Inject constructor(private val apiService: ApiService,private val movieDao: MovieDao,
private val movieRemoteMediator: MovieRemoteMediator) {

    private val TAG = MovieRepo::class.java.simpleName

    private suspend fun getMoviesFromNetwork(): List<MovieRemote>{
        val result = apiService.getMoviesApiResponse(5).results
        Log.i(TAG,result.toString())
        return result
    }

    suspend fun getMoviesNetworkResponse(key: Int): MovieNetworkResponse{
        return apiService.getMoviesApiResponse(key)
    }

    private suspend fun getMoviesFromDb(): List<MovieEntity>{
        val result = movieDao.getAllMovies()
        Log.i(TAG,result.toString())
        return result
    }

    private suspend fun addMoviesToDb(movieEntities: List<MovieEntity>): List<Long> {
        val rowIds = movieDao.insertMovies(movieEntities)
        return rowIds
    }

    @OptIn(ExperimentalPagingApi::class)
    fun loadMovies(): LiveData<PagingData<MovieEntity>>{

        /**
         * Specifies the number of items to load in a single page
         */
        val pageSize = 20


        val pager = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = true),
            remoteMediator = movieRemoteMediator
        ) {
            movieDao.pagingSource()
        }
        Log.i("PagingFlow","8. Pager: $pager")
        return pager.liveData


    }
}