package com.batofgotham.moviereviews.ui.tvshow

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.batofgotham.moviereviews.data.model.TvShow
import com.batofgotham.moviereviews.repository.TvShowsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val tvShowsRepo: TvShowsRepo
): ViewModel() {

    lateinit var tvShow: LiveData<PagingData<TvShow>>

    private val currentQuery = MutableLiveData("friends")

    init {
        getTvShows()
    }

    val searchTvShows = currentQuery.switchMap { queryString ->
        tvShowsRepo.getTvShowsSearch(queryString).cachedIn(viewModelScope)
    }

    private fun getTvShows() {
        viewModelScope.launch {
//            _tvShows.value = tvShowsRepo.getTvShowsFromNetwork()
//            Log.i(TAG, _tvShows.value.toString())
            tvShow = tvShowsRepo.getTvShowsFromNetwork().cachedIn(viewModelScope)
        }
    }

    fun search(search: String) {
        currentQuery.value = search
    }
}