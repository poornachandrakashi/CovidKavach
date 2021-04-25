package com.app.covidhelp

import android.util.Log

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.zone.ZoneRulesException


object DateUtils {

    fun getCurrentDateTime(): Long {
        return Instant.now().toEpochMilli()
    }

    fun getLocalDateTime(datetime: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
    }


    fun getDateTime(date: Long): String? {
        var mdate = ""
        try {
            val dateTime: LocalDateTime = getLocalDateTime(date)
            mdate = dateTime.getMonth()
                    .toString() + " " + dateTime.getDayOfMonth() + "," + dateTime.getYear() + " " + dateTime.getHour() + ":" + dateTime.getMinute()
        } catch (e: ZoneRulesException) {
            Log.d("TAG", "getDateTime: $e")
        }
        return mdate
    }

}