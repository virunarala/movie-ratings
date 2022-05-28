package com.batofgotham.moviereviews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.utils.Converters

@Database(entities = [Movie::class],version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}