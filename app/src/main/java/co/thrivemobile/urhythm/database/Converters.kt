package co.thrivemobile.urhythm.database

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

object Converters {

    private val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    private val timeFormatter = DateTimeFormatter.ISO_OFFSET_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime {
        return value?.let {
            dateFormatter.parse(it, OffsetDateTime::from)
        } ?: OffsetDateTime.now()
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime): String {
        return dateFormatter.format(date)
    }

    @TypeConverter
    @JvmStatic
    fun toOffsetTime(value: String?): OffsetTime {
        return value?.let {
            timeFormatter.parse(it, OffsetTime::from)
        } ?: OffsetTime.now()
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetTime): String {
        return timeFormatter.format(date)
    }
}