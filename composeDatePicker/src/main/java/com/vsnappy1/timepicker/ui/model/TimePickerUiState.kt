package com.vsnappy1.timepicker.ui.model

import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.enums.MinuteGap
import java.util.Calendar

internal data class TimePickerUiState(
    val is24Hour: Boolean = false,
    val minuteGap: MinuteGap = MinuteGap.FIVE,
    val hours: List<String> = Constant.getHours(is24Hour),
    val selectedHourIndex: Int = Constant.getMiddleOfHour(is24Hour) + Calendar.getInstance()[Calendar.HOUR_OF_DAY] + if (Constant.getNearestNextMinute(
            Calendar.getInstance()[Calendar.MINUTE],
            minuteGap
        ) == 0 && Calendar.getInstance()[Calendar.MINUTE] != 0
    ) 1 else 0,
    val minutes: List<String> = Constant.getMinutes(minuteGap),
    val selectedMinuteIndex: Int = Constant.getMiddleOfMinute(minuteGap) + Constant.getNearestNextMinute(
        Calendar.getInstance()[Calendar.MINUTE], minuteGap
    ) / minuteGap.gap,
    val timesOfDay: List<String> = Constant.getTimesOfDay(),
    val selectedTimeOfDayIndex: Int = if ((hours[selectedHourIndex].toInt() + if (is24Hour) 0 else (12 * Calendar.getInstance()[Calendar.AM_PM])) in 12..23) 1 else 0,
)
