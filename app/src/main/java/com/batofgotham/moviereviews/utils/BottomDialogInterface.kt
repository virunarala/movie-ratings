package com.batofgotham.moviereviews.utils

import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.data.model.TvShow

interface BottomDialogInterface {

    fun send(movie: Movie?)

//    fun send(tvShow: TvShow?)
}