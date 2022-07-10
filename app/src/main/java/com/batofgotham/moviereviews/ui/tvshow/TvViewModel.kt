package com.batofgotham.moviereviews.ui.tvshow

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.batofgotham.moviereviews.data.model.TvShow
import com.batofgotham.moviereviews.repository.TvShowRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val tvShowRepo: TvShowRepo
): ViewModel() {

    lateinit var tvShow: LiveData<PagingData<TvShow>>

    private val currentQuery = MutableLiveData("friends")

    init {
        getTvShows()
    }

    val searchTvShows = currentQuery.switchMap { queryString ->
        tvShowRepo.getTvShowsSearch(queryString).cachedIn(viewModelScope)
    }

    private fun getTvShows() {
        viewModelScope.launch {
//            _tvShows.value = tvShowsRepo.getTvShowsFromNetwork()
//            Log.i(TAG, _tvShows.value.toString())
            tvShow = tvShowRepo.getTvShowsFromNetwork().cachedIn(viewModelScope)
        }
    }

    fun search(search: String) {
        currentQuery.value = search
    }
}