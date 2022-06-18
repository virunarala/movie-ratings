package com.batofgotham.moviereviews.utils

import android.util.Log
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Converters"

class Converters {

    @TypeConverter
    fun fromList(list: List<Int>): String{
        return list.toString()
    }

    @TypeConverter
    fun fromString(string: String): List<Int>{
        Log.i(TAG,string)
        val strWithoutBraces = string.substring(1,string.length - 1)
        Log.i(TAG,strWithoutBraces)
        return strWithoutBraces.split(',').map { Integer.parseInt(it.trim()) }
    }
}