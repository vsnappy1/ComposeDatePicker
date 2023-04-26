package com.vsnappy1.composecalendar.data

import com.vsnappy1.composecalendar.enums.Days
import com.vsnappy1.composecalendar.enums.Days.FRIDAY
import com.vsnappy1.composecalendar.enums.Days.MONDAY
import com.vsnappy1.composecalendar.enums.Days.SATURDAY
import com.vsnappy1.composecalendar.enums.Days.SUNDAY
import com.vsnappy1.composecalendar.enums.Days.THURSDAY
import com.vsnappy1.composecalendar.enums.Days.TUESDAY
import com.vsnappy1.composecalendar.enums.Days.WEDNESDAY
import com.vsnappy1.composecalendar.enums.Month
import com.vsnappy1.composecalendar.extension.isLeapYear
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

    fun getMonths(year: Int): List<Month> {
        return listOf(
            Month("January", 31, getFirstDayOfMonth(0, year), 0),
            Month("February", if (year.isLeapYear()) 29 else 28, getFirstDayOfMonth(1, year), 1),
            Month("March", 31, getFirstDayOfMonth(2, year), 2),
            Month("April", 30, getFirstDayOfMonth(3, year), 3),
            Month("May", 31, getFirstDayOfMonth(4, year), 4),
            Month("June", 30, getFirstDayOfMonth(5, year), 5),
            Month("July", 31, getFirstDayOfMonth(6, year), 6),
            Month("August", 31, getFirstDayOfMonth(7, year), 7),
            Month("September", 30, getFirstDayOfMonth(8, year), 8),
            Month("October", 31, getFirstDayOfMonth(9, year), 9),
            Month("November", 30, getFirstDayOfMonth(10, year), 10),
            Month("December", 31, getFirstDayOfMonth(11, year), 11)
        )
    }

    private fun getFirstDayOfMonth(month: Int, year: Int): Days {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return Days.get(calendar.get(Calendar.DAY_OF_WEEK))
    }

    val years = List(100) { it + Calendar.getInstance().get(Calendar.YEAR) - 50 }
}