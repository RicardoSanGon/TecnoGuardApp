package com.example.tecnoguardapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formateDate(date: String): String {
    val dateTime = ZonedDateTime.parse(date)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
    return dateTime.format(formatter).toString()
}