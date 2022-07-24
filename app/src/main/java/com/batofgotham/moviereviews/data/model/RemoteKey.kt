package com.batofgotham.moviereviews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batofgotham.moviereviews.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_REMOTE_KEY)
data class RemoteKey(

    @PrimaryKey(autoGenerate = false)
    val remoteId: Long,

    val prevKey: Int?,

    val nextKey: Int?
)