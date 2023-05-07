package com.vsnappy1.composecalendar.ui.model

import com.vsnappy1.composecalendar.enums.TimeOfDay

data class TimePickerUiState(
    val selectedHour: Int,
    val selectedHourIndex: Int,
    val selectedMinute: Int,
    val selectedMinuteIndex: Int,
    val selectedTimeOfDay: TimeOfDay,
    val selectedTimeOfDayIndex: Int
)