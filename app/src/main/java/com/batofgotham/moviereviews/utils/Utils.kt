package com.batofgotham.moviereviews.utils

import android.util.Log
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private const val TAG = "Utils"

    fun getDate(dateString: String?): Date?{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var date: Date? = null
        try{
            date = dateFormat.parse(dateString)
        }
        catch(e: Exception){
            Log.i(TAG,"Failed to parse date")
        }
        return date
    }

    fun getYear(date: Date?): Int?{
        val calendar = Calendar.getInstance()
        var year: Int? = null
        try{
            calendar.setTime(date)
            year = calendar.get(Calendar.YEAR)
        }
        catch (e: Exception){
            Log.i(TAG,"Error! Could not get the year. The date might be null")
        }

        return year
    }

//    fun remoteFromEntity(movieEntity: MovieEntity): MovieRemote{
//        movieEntity.apply {
//            return MovieRemote(
//                posterPath = this.posterPath,
//                adult = this.adult,
//                overview = this.overview,
//                releaseDate = this.releaseDate,
//                genreIds = this.genreIds,
//                id = this.id,
//                originalTitle = this.originalTitle,
//                originalLanguage = this.originalLanguage,
//                title = this.title,
//                backdropPath = this.backdropPath,
//                popularity = this.popularity,
//                voteCount = this.voteCount,
//                video = this.video,
//                voteAverage = this.voteAverage
//            )
//        }
//    }

    fun MovieEntity.fromRemote(movieRemote: MovieRemote): MovieEntity{
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