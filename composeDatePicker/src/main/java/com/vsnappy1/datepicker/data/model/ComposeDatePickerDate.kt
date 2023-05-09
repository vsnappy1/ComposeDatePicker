package com.vsnappy1.datepicker.data.model

import android.icu.util.Calendar

data class ComposeDatePickerDate(
    val year: Int,
    val month: Int,
    val day: Int
)

object DefaultDate {
    private val calendar = Calendar.getInstance()
    val defaultDate = ComposeDatePickerDate(
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
}
