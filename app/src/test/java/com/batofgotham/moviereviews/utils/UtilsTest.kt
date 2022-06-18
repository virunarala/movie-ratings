package com.batofgotham.moviereviews.utils

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import java.util.*

internal class UtilsTest{

    @Test
    fun getDate_returnsDateFromString(){
        val dateString = "2049-11-05"

        val date = Utils.getDate(dateString)
        val year = Utils.getYear(date)

        assertThat(year,  `is`(2049))
    }
}