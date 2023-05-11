package com.vsnappy1.timepicker.ui.model

import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.enums.MinuteGap
import java.util.Calendar

data class TimePickerUiState(
    val is24Hour: Boolean = false,
    val minuteGap: MinuteGap = MinuteGap.FIVE,
    val hours: List<String> = Constant.getHours(is24Hour),
    val selectedHourIndex: Int = Constant.getMiddleOfHour(is24Hour) + Calendar.getInstance()[if (is24Hour) Calendar.HOUR_OF_DAY else Calendar.HOUR] - if (is24Hour) 0 else 1,
    val minutes: List<String> = Constant.getMinutes(minuteGap),
    val selectedMinuteIndex: Int = Constant.getMiddleOfMinute(minuteGap) + Constant.getNearestNextMinute(
        Calendar.getInstance()[Calendar.MINUTE], minuteGap
    ) / minuteGap.gap,
    val timesOfDay: List<String> = Constant.getTimesOfDay(),
    val selectedTimeOfDayIndex: Int = Calendar.getInstance()[Calendar.AM_PM],
)
