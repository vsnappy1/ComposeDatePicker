package com.vsnappy1.timepicker.ui.model

import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import java.util.Calendar

data class TimePickerUiState(
    val is24Hour: Boolean = false,
    val minuteGap: MinuteGap = MinuteGap.FIVE,
    val hours: List<String> = Constant.getHours(is24Hour),
    val selectedHour: Int = Calendar.getInstance()[Calendar.HOUR] + 1,
    val selectedHourIndex: Int = Constant.getMiddleOfHour(is24Hour) + Calendar.getInstance()[Calendar.HOUR] + 1,
    val minutes: List<String> = Constant.getMinutes(minuteGap),
    val selectedMinute: Int = Constant.getNearestNextMinute(
        Calendar.getInstance()[Calendar.MINUTE],
        minuteGap
    ),
    val selectedMinuteIndex: Int = Constant.getMiddleOfMinute(minuteGap) + selectedMinute / minuteGap.gap + 1,
    val timesOfDay: List<String> = Constant.getTimesOfDay(),
    val selectedTimeOfDay: TimeOfDay = if (Calendar.getInstance()[Calendar.AM_PM] == 0) TimeOfDay.AM else TimeOfDay.PM,
    val selectedTimeOfDayIndex: Int = Calendar.getInstance()[Calendar.AM_PM],
)