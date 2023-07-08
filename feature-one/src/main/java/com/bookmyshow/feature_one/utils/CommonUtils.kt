package com.bookmyshow.feature_one.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object CommonUtils {

    fun convertCodeToDate(apiDate:String):String{
        val formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(apiDate, formatter)

        val month = date.monthValue
        val day = date.dayOfMonth
        return "$month\n$day"
    }

}