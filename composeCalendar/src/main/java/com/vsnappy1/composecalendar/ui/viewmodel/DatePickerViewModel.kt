package com.vsnappy1.composecalendar.ui.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.data.model.ComposeDatePickerDate
import com.vsnappy1.composecalendar.data.model.Month
import com.vsnappy1.composecalendar.ui.model.DatePickerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DatePickerViewModel : ViewModel() {

    private var _uiState = MutableLiveData(DatePickerUiState())
    val uiState: LiveData<DatePickerUiState> = _uiState
    private lateinit var availableMonths: List<Month>

    init {
        uiState.value?.let {
            availableMonths = Constant.getMonths(it.selectedYear)
        }
    }

    private fun updateCurrentVisibleMonth(month: Int) {
        _uiState.value?.apply {
            _uiState.value = this.copy(currentVisibleMonth = availableMonths[month])
        }
    }

    fun updateSelectedMonthIndex(index: Int) {
        _uiState.value?.apply {
            _uiState.value = this.copy(
                selectedMonthIndex = index,
                currentVisibleMonth = availableMonths[index % 12]
            )
        }
    }

    fun updateSelectedDayAndMonth(day: Int) {
        _uiState.value = _uiState.value?.let {
            _uiState.value?.copy(
                selectedDayOfMonth = day,
                selectedMonth = it.currentVisibleMonth
            )
        }
    }

    fun updateUiState(uiState: DatePickerUiState) {
        _uiState.value = uiState
    }

    fun moveToNextMonth() {
        _uiState.value?.apply {
            if (currentVisibleMonth.number == 11) { // if it is december
                val newYear = selectedYear + 1
                availableMonths = Constant.getMonths(newYear)
                _uiState.value = _uiState.value?.copy(
                    selectedYear = newYear,
                    selectedYearIndex = selectedYearIndex + 1,
                    selectedMonthIndex = getAdjustedSelectedMonthIndex(selectedMonthIndex + 1),
                    currentVisibleMonth = availableMonths[0]
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    currentVisibleMonth = availableMonths[currentVisibleMonth.number + 1],
                    selectedMonthIndex = getAdjustedSelectedMonthIndex(selectedMonthIndex + 1),
                )
            }
        }
    }

    fun moveToPreviousMonth() {
        _uiState.value?.apply {
            if (currentVisibleMonth.number == 0) { // if it is december
                val newYear = selectedYear - 1
                availableMonths = Constant.getMonths(newYear)
                _uiState.value = _uiState.value?.copy(
                    selectedYear = newYear,
                    selectedYearIndex = selectedYearIndex - 1,
                    selectedMonthIndex = getAdjustedSelectedMonthIndex(selectedMonthIndex - 1),
                    currentVisibleMonth = availableMonths[11]
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    currentVisibleMonth = availableMonths[currentVisibleMonth.number - 1],
                    selectedMonthIndex = getAdjustedSelectedMonthIndex(selectedMonthIndex - 1),
                )
            }
        }
    }

    private fun getAdjustedSelectedMonthIndex(index: Int) = Constant.monthMiddleIndex + index % 12

    fun updateSelectedYearIndex(index: Int) {
        availableMonths = Constant.getMonths(Constant.years[index])
        _uiState.value = _uiState.value?.copy(
            selectedYearIndex = index,
            selectedYear = Constant.years[index],
            currentVisibleMonth = availableMonths[_uiState.value?.currentVisibleMonth?.number ?: 0]
        )
    }

    fun toggleIsMonthYearViewVisible() {
        if (_uiState.value?.isMonthYearViewVisible == true) {
            viewModelScope.launch {
                delay(250)
                _uiState.value = _uiState.value?.let {
                    it.copy(
                        selectedMonthIndex = getAdjustedSelectedMonthIndex(it.selectedMonthIndex)
                    )
                }
            }
        }
        _uiState.value = _uiState.value?.let {
            it.copy(
                isMonthYearViewVisible = !it.isMonthYearViewVisible,
            )
        }
    }

    fun setDate(date: ComposeDatePickerDate) {
        val yearMin = Constant.years.first()
        val yearMax = Constant.years.last()

        if (date.year < yearMin || date.year > yearMax) {
            throw IllegalArgumentException("Invalid year: ${date.year}, year value must be between $yearMin to $yearMax.")
        }
        if (date.month < 0 || date.month > 11) {
            throw IllegalArgumentException("Invalid month: ${date.month}, month value must be between 0 to 11.")
        }

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.MONTH, date.month)

        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        if (date.day < 1) {
            throw IllegalArgumentException("Invalid day: ${date.day}, day value must be greater than zero.")
        }
        if (date.day > maxDays) {
            throw IllegalArgumentException("Invalid day: ${date.day}, day value must be less than equal to $maxDays for given month.")
        }

        val index = Constant.years.indexOf(date.year)
        updateSelectedYearIndex(index)
        updateCurrentVisibleMonth(date.month)
        updateSelectedDayAndMonth(date.day)
    }
}