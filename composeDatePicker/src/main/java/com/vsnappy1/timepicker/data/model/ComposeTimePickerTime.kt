package com.vsnappy1.timepicker.data.model

import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.enums.MinuteGap
import java.util.Calendar

class ComposeTimePickerTime(val hour: Int, val minute: Int) {

    companion object DefaultTime {
        fun getTime(minuteGap: MinuteGap): ComposeTimePickerTime {
            val minute =
                Constant.getNearestNextMinute(Calendar.getInstance()[Calendar.MINUTE], minuteGap)
            return ComposeTimePickerTime(
                Calendar.getInstance()[Calendar.HOUR_OF_DAY],
                minute
            )
        }
    }
}


