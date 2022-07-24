package com.batofgotham.moviereviews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batofgotham.moviereviews.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_MOVIE)
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    val remoteId: Long = 0L,

    val posterPath: String? = null,

    val adult: Boolean?,

    val overview:String?,

    val releaseDate:String?,

    val genreIds: List<Int>?,

    val id: Int,

    val originalTitle: String?,

    val originalLanguage: String?,

    val title: String?,

    val backdropPath: String? = null,

    val popularity: Double?,

    val voteCount: Int?,

    val video: Boolean?,

    val voteAverage: Double?
){

    companion object{

        fun fromRemote(movieRemote: MovieRemote): MovieEntity{
            movieRemote.apply {
                return MovieEntity(
                    posterPath = this.posterPath,
                    adult = this.adult,
                    overview = this.overview,
                    releaseDate = this.releaseDate,
                    genreIds = this.genreIds,
                    id = this.id,
                    originalTitle = this.originalTitle,
                    originalLanguage = this.originalLanguage,
                    title = this.title,
                    backdropPath = this.backdropPath,
                    popularity = this.popularity,
                    voteCount = this.voteCount,
                    video = this.video,
                    voteAverage = this.voteAverage
                )
            }
        }

    }
}