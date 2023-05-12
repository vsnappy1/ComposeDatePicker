package com.vsnappy1.timepicker.data.model

import android.content.Context
import android.text.format.DateFormat
import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import java.util.Calendar

sealed class ComposeTimePickerTime {
    class TwelveHourTime(val hour: Int, val minute: Int, val timeOfDay: TimeOfDay) :
        ComposeTimePickerTime()

    class TwentyFourHourTime(val hour: Int, val minute: Int) : ComposeTimePickerTime()
}


object DefaultTime {
    fun getTime(context: Context, minuteGap: MinuteGap, is24Hour: Boolean?): ComposeTimePickerTime {
        val calendar = Calendar.getInstance()
        val is24 = is24Hour ?: DateFormat.is24HourFormat(context)
        val minute =
            Constant.getNearestNextMinute(Calendar.getInstance()[Calendar.MINUTE], minuteGap)
        return if (is24) {
            ComposeTimePickerTime.TwentyFourHourTime(
                calendar[Calendar.HOUR_OF_DAY],
                minute
            )
        } else {
            ComposeTimePickerTime.TwelveHourTime(
                calendar[Calendar.HOUR] + 1,
                minute,
                if (calendar[Calendar.AM_PM] == 1) TimeOfDay.PM else TimeOfDay.AM
            )
        }
    }
}