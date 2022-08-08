package com.batofgotham.moviereviews.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import com.batofgotham.moviereviews.utils.Constants

@Dao
abstract interface MovieDao {

    /**
     * Inserts a movie into database and returns the rowId of the inserted movie
     *
     * This method is redundant. Can be removed if no usage is found.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity): Long

    /**
     * Inserts a list of movies into database and returns a list of rowIds of the inserted movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>): List<Long>

    /**
     * Returns a list of all the movies in the database
     */
    @Query("SELECT * FROM ${Constants.TABLE_NAME_MOVIE}")
    suspend fun getAllMovies(): List<MovieEntity>

    /**
     * Clears all the data in the Movies table
     */
    @Query("DELETE FROM ${Constants.TABLE_NAME_MOVIE}")
    suspend fun clearAllMovies()

    /**
     * At any given time, there will only be one page of data cached in the database
     */
    @Query("SELECT * FROM ${Constants.TABLE_NAME_MOVIE}")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    /**
     * Returns an integer indicating the number of items in the database
     */
    @Query("SELECT COUNT(*) FROM ${Constants.TABLE_NAME_MOVIE}")
    suspend fun size(): Int

    /**
     * Deletes the top n items from the table
     */
    @Query("DELETE FROM ${Constants.TABLE_NAME_MOVIE} WHERE remoteId IN (SELECT remoteId from ${Constants.TABLE_NAME_MOVIE} ORDER BY remoteId LIMIT :numberOfMovies)")
    suspend fun deleteMoviesFromTop(numberOfMovies: Int)

    /**
     * Deletes the bottom n items from the table
     */
    @Query("DELETE FROM ${Constants.TABLE_NAME_MOVIE} WHERE remoteId IN (SELECT remoteId from ${Constants.TABLE_NAME_MOVIE} ORDER BY remoteId DESC LIMIT :numberOfMovies)")
    suspend fun deleteMoviesFromBottom(numberOfMovies: Int)

}