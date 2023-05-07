package com.vsnappy1.composecalendar.data.model

import android.icu.util.Calendar

data class ComposeDatePickerDate(
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH),
    val day: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
)
