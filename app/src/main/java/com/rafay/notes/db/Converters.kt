package com.rafay.notes.db

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object Converters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(dateTime: String?): OffsetDateTime? {
        return dateTime?.let {
            formatter.parse(it, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(dateTime: OffsetDateTime?): String? {
        return dateTime?.format(formatter)
    }
}