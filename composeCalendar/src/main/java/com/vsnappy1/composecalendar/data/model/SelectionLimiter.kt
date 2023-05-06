package com.vsnappy1.composecalendar.data.model

import com.vsnappy1.composecalendar.extension.isEqual
import java.util.Calendar

class SelectionLimiter(
    private val fromDate: ComposeDatePickerDate? = null,
    private val toDate: ComposeDatePickerDate? = null
) {
    fun isWithinRange(date: ComposeDatePickerDate): Boolean {
        if (fromDate == null && toDate == null) return true

        val fromDate =
            fromDate?.let {
                Calendar.getInstance().apply {
                    set(it.year, it.month, it.day, 0, 0)
                }
            }
        val toDate =
            toDate?.let {
                Calendar.getInstance().apply {
                    set(it.year, it.month, it.day, 0, 0)
                }
            }

        val selectedDate = Calendar.getInstance().apply {
            set(date.year, date.month, date.day, 0, 0)
        }

        return if (fromDate != null) {
            selectedDate.after(fromDate) || selectedDate.isEqual(fromDate)
        } else if (toDate != null) {
            selectedDate.before(toDate) || selectedDate.isEqual(toDate)
        } else {
            (selectedDate.before(toDate) && selectedDate.after(fromDate)) ||
                    (selectedDate.isEqual(toDate) || selectedDate.isEqual(fromDate))
        }
    }
}

