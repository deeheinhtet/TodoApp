package com.dee.todoapp.utils

import com.dee.todoapp.Constants
import java.util.Date
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Hein Htet
 */


fun Date.toDisplayDate(): String {
    try {
        val instant: Instant = Instant.ofEpochMilli(this.time)
        val zonedDateTime: ZonedDateTime = instant.atZone(ZoneId.systemDefault())
        val desiredFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_1)
        val formattedDate: String = zonedDateTime.format(desiredFormatter)
        return formattedDate
    } catch (e: Exception) {
        println(e.printStackTrace())
        return ""
    }
}