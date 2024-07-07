package com.dee.todoapp.data.local.database.converter

import androidx.room.TypeConverter
import java.util.Date

/**
 * Created by Hein Htet
 */
object DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        if (timestamp == null) {
            return null
        }
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        if (date == null) {
            return null
        }
        return date.time
    }
}