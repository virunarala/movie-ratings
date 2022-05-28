package com.batofgotham.moviereviews.utils

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromList(list: List<Int>): String{
        return list.toString()
    }

    @TypeConverter
    fun fromString(string: String): List<Int>{
        return string.split(',').map { Integer.parseInt(it) }
    }
}