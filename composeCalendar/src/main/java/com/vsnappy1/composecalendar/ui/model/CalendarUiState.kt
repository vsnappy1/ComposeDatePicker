package com.vsnappy1.composecalendar.ui.model

import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.enums.Month
import java.util.Calendar

data class CalendarUiState(
    val selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val selectedYearIndex: Int = Constant.years.size / 2,
    val availableMonths: List<Month> = Constant.getMonths(selectedYear),
    val selectedMonth: Month = availableMonths[Calendar.getInstance().get(Calendar.MONTH)],
    val currentVisibleMonth: Month = selectedMonth,
    val selectedDayOfMonth: Int? = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    val availableYears: List<Int> = Constant.years,
    val isMonthYearViewVisible: Boolean = true
)