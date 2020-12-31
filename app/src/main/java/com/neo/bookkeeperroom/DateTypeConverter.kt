package com.neo.bookkeeperroom

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

    // take long value and ret date
    @TypeConverter
    fun toDate(value: Long?): Date?{
        return if(value == null) null else Date(value)
    }


    // takes date value and ret longs
    @TypeConverter
    fun toLong(value: Date?): Long?{
        return value?.time
    }
}