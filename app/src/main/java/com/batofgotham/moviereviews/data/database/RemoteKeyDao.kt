package com.batofgotham.moviereviews.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batofgotham.moviereviews.data.model.RemoteKey
import com.batofgotham.moviereviews.utils.Constants

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM ${Constants.TABLE_NAME_REMOTE_KEY} WHERE remoteId = :id")
    suspend fun getRemoteKey(id: Long): RemoteKey


    @Query("DELETE FROM ${Constants.TABLE_NAME_REMOTE_KEY}")
    suspend fun clearAll()


    /**
     * Deletes the top n items from the table
     */
    @Query("DELETE FROM ${Constants.TABLE_NAME_REMOTE_KEY} WHERE remoteId IN (SELECT remoteId from ${Constants.TABLE_NAME_REMOTE_KEY} ORDER BY remoteId LIMIT :numberOfMovies)")
    suspend fun deleteRemoteKeysFromTop(numberOfMovies: Int)

    /**
     * Deletes the bottom n items from the table
     */
    @Query("DELETE FROM ${Constants.TABLE_NAME_REMOTE_KEY} WHERE remoteId IN (SELECT remoteId from ${Constants.TABLE_NAME_REMOTE_KEY} ORDER BY remoteId DESC LIMIT :numberOfMovies)")
    suspend fun deleteRemoteKeysFromBottom(numberOfMovies: Int)

}