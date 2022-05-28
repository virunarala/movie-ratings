package com.batofgotham.moviereviews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.remote.movies.MoviesApi
import com.batofgotham.moviereviews.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepository: MoviesRepository): ViewModel() {

    private val TAG = "MovieViewModel"


    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        Log.i(TAG,"ViewModel init called")
        getMovies()
        Log.i(TAG,"ViewModel getMovies() called")
        if(moviesRepository==null)
            Log.i(TAG,"Repo null")
    }



    private fun getMovies(){
        viewModelScope.launch {
            _movies.value = moviesRepository.getMoviesFromNetwork()
            Log.i(TAG,_movies.value.toString())
        }
    }

}