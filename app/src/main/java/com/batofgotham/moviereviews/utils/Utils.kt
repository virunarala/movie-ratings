package com.batofgotham.moviereviews.utils

import android.util.Log
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
}