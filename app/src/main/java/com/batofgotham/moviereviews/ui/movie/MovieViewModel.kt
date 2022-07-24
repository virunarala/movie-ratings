package com.batofgotham.moviereviews.ui.movie

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.batofgotham.moviereviews.data.model.Configuration
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import com.batofgotham.moviereviews.repository.ConfigRepo
import com.batofgotham.moviereviews.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepo,
    private val configRepo: ConfigRepo
) : ViewModel() {

    private val TAG = "MovieViewModel"

    private val _apiConfig = MutableLiveData<Configuration>()
    val apiConfig: LiveData<Configuration>
        get() = _apiConfig

    fun getMovies(): LiveData<PagingData<MovieEntity>>{
        val movies = movieRepo.loadMovies()
        Log.i("PagingFlow","9. ${movies.value}")
        return movies
    }



    private fun getApiConfig() {
        viewModelScope.launch {
            _apiConfig.value = configRepo.getApiConfig()
        }
    }

}