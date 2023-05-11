package com.vsnappy1.datepicker.data.model

import com.vsnappy1.datepicker.enums.Days

data class Month(
    val name: String,
    val numberOfDays: Int,
    val firstDayOfMonth: Days,
    val number: Int
)