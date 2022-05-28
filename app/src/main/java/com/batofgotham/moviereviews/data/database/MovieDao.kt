package com.batofgotham.moviereviews.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.utils.Constants

@Dao
abstract interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM ${Constants.TABLE_NAME_MOVIE}")
    suspend fun getAllMovies(): List<Movie>
}