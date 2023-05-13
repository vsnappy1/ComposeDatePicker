package com.vsnappy1.datepicker.ui.model

import com.vsnappy1.datepicker.data.Constant
import com.vsnappy1.datepicker.data.model.Month
import java.util.Calendar
import kotlin.streams.toList

internal data class DatePickerUiState(
    val selectedYear: Int = Calendar.getInstance()[Calendar.YEAR],
    val selectedYearIndex: Int = Constant.years.size / 2,
    val selectedMonth: Month = Constant.getMonths(selectedYear)[Calendar.getInstance()[Calendar.MONTH]],
    val selectedMonthIndex: Int = Constant.getMiddleOfMonth() + selectedMonth.number,
    val currentVisibleMonth: Month = selectedMonth,
    val selectedDayOfMonth: Int = Calendar.getInstance()[Calendar.DAY_OF_MONTH],
    val years: List<String> = Constant.years.stream().map { "$it" }.toList(),
    val months: List<String> = Constant.getMonths(),
    val isMonthYearViewVisible: Boolean = false
)