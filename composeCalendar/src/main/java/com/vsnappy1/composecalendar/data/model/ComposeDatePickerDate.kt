package com.vsnappy1.composecalendar.data.model

import android.icu.util.Calendar

data class ComposeDatePickerDate(
    val year: Int,
    val month: Int,
    val day: Int
)

object DefaultDate {
    private val calendar = Calendar.getInstance()
    val defaultDate = ComposeDatePickerDate(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
