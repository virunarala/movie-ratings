package com.batofgotham.moviereviews.ui.movie

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepo: MovieRepo) : ViewModel() {

    fun getMovies(): LiveData<PagingData<MovieEntity>>{
        val movies = movieRepo.loadMovies()
        Log.i("PagingFlow","9. ${movies.value}")
        return movies
    }

}