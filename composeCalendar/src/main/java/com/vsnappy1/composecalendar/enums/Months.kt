package com.vsnappy1.composecalendar.enums

import com.vsnappy1.composecalendar.enums.Days.*

enum class Months(val value: String, val numberOfDays: Int, val firstDayOfMonth: Days) {
    JANUARY     ("January", 31, SUNDAY),
    FEBRUARY    ("February", 28, WEDNESDAY),
    MARCH       ("March", 31, TUESDAY),
    APRIL       ("April", 30, SATURDAY),
    MAY         ("May", 31, MONDAY),
    JUNE        ("June", 30, THURSDAY),
    JULY        ("July", 31, SATURDAY),
    AUGUST      ("August", 31, TUESDAY),
    SEPTEMBER   ("September", 30, FRIDAY),
    OCTOBER     ("October", 31, SUNDAY),
    NOVEMBER    ("November", 30, WEDNESDAY),
    DECEMBER    ("December", 31, FRIDAY)
}