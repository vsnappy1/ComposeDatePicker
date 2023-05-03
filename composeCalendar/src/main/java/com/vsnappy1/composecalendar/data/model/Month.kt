package com.vsnappy1.composecalendar.data.model

import com.vsnappy1.composecalendar.enums.Days

data class Month(
    val name: String,
    val numberOfDays: Int,
    val firstDayOfMonth: Days,
    val number: Int
)