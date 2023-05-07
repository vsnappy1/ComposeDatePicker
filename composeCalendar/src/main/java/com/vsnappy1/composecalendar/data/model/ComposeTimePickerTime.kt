package com.vsnappy1.composecalendar.data.model

import android.content.Context
import android.text.format.DateFormat
import com.vsnappy1.composecalendar.enums.MinuteGap
import com.vsnappy1.composecalendar.enums.TimeOfDay
import java.util.Calendar

sealed class ComposeTimePickerTime {
    class TwelveHourTime(
        hour: Int,
        minute: Int,
        time: TimeOfDay,
        minuteGap: MinuteGap = MinuteGap.FIVE
    ) : ComposeTimePickerTime()

    class TwentyFourHourTime(hour: Int, minute: Int, minuteGap: MinuteGap = MinuteGap.FIVE) :
        ComposeTimePickerTime()
}


object DefaultTime {
    fun getTime(context: Context): ComposeTimePickerTime {
        val calendar = Calendar.getInstance()
        val minute = calendar[Calendar.MINUTE]
        return if (DateFormat.is24HourFormat(context)) {
            ComposeTimePickerTime.TwentyFourHourTime(
                calendar[Calendar.HOUR_OF_DAY],
                minute
            )
        } else {
            ComposeTimePickerTime.TwelveHourTime(
                calendar[Calendar.HOUR],
                minute,
                if (calendar[Calendar.AM_PM] == 1) TimeOfDay.PM else TimeOfDay.AM
            )
        }
    }
}