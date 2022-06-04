package com.batofgotham.moviereviews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.batofgotham.moviereviews.data.model.Configuration
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.model.TvShows
import com.batofgotham.moviereviews.repository.ConfigRepo
import com.batofgotham.moviereviews.repository.MovieRepo
import com.batofgotham.moviereviews.repository.TvShowsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepo,
    private val configRepo: ConfigRepo,
    private val tvShowsRepo: TvShowsRepo
) : ViewModel() {

    private val TAG = "MovieViewModel"

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

//    private val _tvShows: LiveData<List<TvShows>>? = null
//    val tvShows: LiveData<List<TvShows>>
//        get() = _tvShows

    lateinit var tvShows: LiveData<PagingData<TvShows>>

    private val _apiConfig = MutableLiveData<Configuration>()
    val apiConfig: LiveData<Configuration>
        get() = _apiConfig

    private val currentQuery = MutableLiveData("friends")

    val searchTvShows = currentQuery.switchMap { queryString ->
        tvShowsRepo.getTvShowsSearch(queryString).cachedIn(viewModelScope)
    }
//    val searchTvShows: LiveData<List<TvShows>>
//        get() = _searchTvShows

    init {
        getMovies()
        Log.i(TAG, "ViewModel getMovies() called")
        if (movieRepo == null)
            Log.i(TAG, "Repo null")

        getTvShows()
        Log.i(TAG, "ViewModel getTvShows() called")
    }

    private fun getMovies() {
        viewModelScope.launch {
            _movies.value = movieRepo.getMoviesFromNetwork()
            Log.i(TAG, _movies.value.toString())
        }
    }

    private fun getTvShows() {
        viewModelScope.launch {
//            _tvShows.value = tvShowsRepo.getTvShowsFromNetwork()
//            Log.i(TAG, _tvShows.value.toString())
            tvShows = tvShowsRepo.getTvShowsFromNetwork().cachedIn(viewModelScope)
        }
    }

    private fun getApiConfig() {
        viewModelScope.launch {
            _apiConfig.value = configRepo.getApiConfig()
        }
    }

    fun search(search: String) {
        currentQuery.value = search
    }

}