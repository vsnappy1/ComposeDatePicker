package com.vsnappy1.composecalendar.data

import com.vsnappy1.composecalendar.enums.Days.*
import com.vsnappy1.composecalendar.enums.Months.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar

object Constant {
    val days = listOf(
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    )

    val months = listOf(
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER
    )

    val years = List(100) { it + Calendar.getInstance().get(Calendar.YEAR) }
}