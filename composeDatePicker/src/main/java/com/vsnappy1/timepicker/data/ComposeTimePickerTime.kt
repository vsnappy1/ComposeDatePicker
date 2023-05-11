package com.vsnappy1.timepicker.data

import android.content.Context
import android.text.format.DateFormat
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import java.util.Calendar

sealed class ComposeTimePickerTime {
    class TwelveHourTime(hour: Int, minute: Int, timeOfDay: TimeOfDay) : ComposeTimePickerTime()

    class TwentyFourHourTime(hour: Int, minute: Int) : ComposeTimePickerTime()
}


object DefaultTime {
    fun getTime(context: Context, minuteGap: MinuteGap, is24Hour: Boolean?): ComposeTimePickerTime {
        val calendar = Calendar.getInstance()
        val is24 = is24Hour ?: DateFormat.is24HourFormat(context)
        return if (is24) {
            ComposeTimePickerTime.TwentyFourHourTime(
                calendar[Calendar.HOUR_OF_DAY],
                0
            )
        } else {
            ComposeTimePickerTime.TwelveHourTime(
                calendar[Calendar.HOUR] + 1,
                0,
                if (calendar[Calendar.AM_PM] == 1) TimeOfDay.PM else TimeOfDay.AM
            )
        }
    }
}