package com.vsnappy1.datepicker.data

import com.vsnappy1.datepicker.enums.Days
import com.vsnappy1.datepicker.enums.Days.FRIDAY
import com.vsnappy1.datepicker.enums.Days.MONDAY
import com.vsnappy1.datepicker.enums.Days.SATURDAY
import com.vsnappy1.datepicker.enums.Days.SUNDAY
import com.vsnappy1.datepicker.enums.Days.THURSDAY
import com.vsnappy1.datepicker.enums.Days.TUESDAY
import com.vsnappy1.datepicker.enums.Days.WEDNESDAY
import com.vsnappy1.datepicker.data.model.Month
import com.vsnappy1.extension.isLeapYear
import java.util.Calendar

internal object Constant {
    private const val repeatCount: Int = 200

    val days = listOf(
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    )

    private val monthNames = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
    )

    fun getMonths(): List<String> {
        val list = mutableListOf<String>()
        for (i in 1..repeatCount) {
            list.addAll(monthNames)
        }
        return list
    }

    fun getMiddleOfMonth(): Int {
        return 12 * (repeatCount / 2)
    }

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
        return Days.get(calendar[Calendar.DAY_OF_WEEK])
    }

    val years = List(200) { it + Calendar.getInstance()[Calendar.YEAR] - 100 }
}