package com.matheus.gerenciadorcontacorrente.data.localstore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object TypeConverterUtil {
        private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        private val dayOfWeekValues by lazy(LazyThreadSafetyMode.NONE) { DayOfWeek.values() }

        @TypeConverter
        @JvmStatic
        fun toOffsetDateTime(value: String?) = value?.let { formatter.parse(value, OffsetDateTime::from) }

        @TypeConverter
        @JvmStatic
        fun fromOffsetDateTime(date: OffsetDateTime?): String? = date?.format(formatter)

        @TypeConverter
        @JvmStatic
        fun toZoneId(value: String?) = value?.let { ZoneId.of(it) }

        @TypeConverter
        @JvmStatic
        fun fromZoneId(value: ZoneId?) = value?.id

        @TypeConverter
        @JvmStatic
        fun toLocalTime(value: String?) = value?.let { LocalTime.parse(value) }

        @TypeConverter
        @JvmStatic
        fun fromLocalTime(value: LocalTime?) = value?.format(DateTimeFormatter.ISO_LOCAL_TIME)


}