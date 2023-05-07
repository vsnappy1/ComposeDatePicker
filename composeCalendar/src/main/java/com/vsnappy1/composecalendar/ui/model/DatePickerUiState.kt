package com.vsnappy1.composecalendar.ui.model

import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.data.model.Month
import java.util.Calendar

data class DatePickerUiState(
    val selectedYear: Int = Calendar.getInstance()[Calendar.YEAR],
    val selectedYearIndex: Int = Constant.years.size / 2,
    val selectedMonth: Month = Constant.getMonths(selectedYear)[Calendar.getInstance()[Calendar.MONTH]],
    val selectedMonthIndex: Int = 36 + selectedMonth.number,
    val currentVisibleMonth: Month = selectedMonth,
    val selectedDayOfMonth: Int? = Calendar.getInstance()[Calendar.DAY_OF_MONTH],
    val availableYears: List<Int> = Constant.years,
    val isMonthYearViewVisible: Boolean = false
)