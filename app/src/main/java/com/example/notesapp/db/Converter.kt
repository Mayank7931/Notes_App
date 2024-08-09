package com.example.notesapp.db

import androidx.room.TypeConverter
import java.sql.Date

class Converter {


    @TypeConverter
    fun fromDate(date: java.util.Date): Long{
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): java.util.Date {
        return java.util.Date(time)
    }
}