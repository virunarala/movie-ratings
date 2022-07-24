package com.batofgotham.moviereviews.utils

import com.batofgotham.moviereviews.data.model.MovieEntity

interface BottomDialogInterface {

    fun onMovieSelected(movie: MovieEntity?)

//    fun send(tvShow: TvShow?)
}