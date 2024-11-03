package com.example.todo.presentance.utils

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun dateAndTimeFormat(dateTime:Long):String{

    val sdf = SimpleDateFormat("EEEE,dd MMMM yyyy hh:mm a", Locale.getDefault())
    val date = Date(dateTime)
    return sdf.format(date)

}

fun zoneDateTime(dateTime:String):String{
    val sdf = SimpleDateFormat("EEEE,dd MMMM yyyy hh:mm a", Locale.getDefault())
    val date = Date()
    return sdf.format(dateTime)
}