package com.batofgotham.moviereviews.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.utils.Constants

@Dao
abstract interface MovieDao {

    /**
     * Inserts a movie into database and returns the rowId of the inserted movie
     *
     * This method is redundant. Can be removed if no usage is found.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie): Long

    /**
     * Inserts a list of movies into database and returns a list of rowIds of the inserted movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>): List<Long>

    /**
     * Returns a list of all the movies in the database
     */
    @Query("SELECT * FROM ${Constants.TABLE_NAME_MOVIE}")
    suspend fun getAllMovies(): List<Movie>
}