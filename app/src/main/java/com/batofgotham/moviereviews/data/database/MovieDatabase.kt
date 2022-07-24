package com.batofgotham.moviereviews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import com.batofgotham.moviereviews.data.model.RemoteKey
import com.batofgotham.moviereviews.utils.Converters

@Database(entities = [MovieEntity::class, RemoteKey::class],version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: RemoteKeyDao
}