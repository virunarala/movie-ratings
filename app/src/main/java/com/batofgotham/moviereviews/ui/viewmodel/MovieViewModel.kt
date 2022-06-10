package com.batofgotham.moviereviews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.batofgotham.moviereviews.data.model.Configuration
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.repository.ConfigRepo
import com.batofgotham.moviereviews.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepo: MovieRepo,private val configRepo: ConfigRepo): ViewModel() {

    private val TAG = "MovieViewModel"


//    private val _movies = MutableLiveData<List<Movie>>()
//    val movies: LiveData<List<Movie>>
//        get() = _movies

    private val _apiConfig = MutableLiveData<Configuration>()
    val apiConfig: LiveData<Configuration>
        get() = _apiConfig

    init {
        getMovies()
        Log.i(TAG,"ViewModel getMovies() called")
        if(movieRepo==null)
            Log.i(TAG,"Repo null")
    }



    fun getMovies(): LiveData<PagingData<Movie>>{
        return movieRepo.loadMovies().cachedIn(viewModelScope)
    }

    private fun getApiConfig(){
        viewModelScope.launch {
            _apiConfig.value = configRepo.getApiConfig()
        }
    }

}