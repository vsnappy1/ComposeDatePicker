package com.vsnappy1.composecalendar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.ui.model.CalendarUiState

class CalendarViewModel : ViewModel() {

    private var _uiState = MutableLiveData(CalendarUiState())
    val uiState: LiveData<CalendarUiState> = _uiState

    fun updateSelectedMonth(month: Int) {
        _uiState.value?.apply {
            _uiState.value = this.copy(currentVisibleMonth = availableMonths[month])
        }
    }

    fun updateCurrentVisibleMonth(month: Int) {
        _uiState.value?.apply {
            _uiState.value = this.copy(currentVisibleMonth = availableMonths[month])
        }
    }

    fun updateSelectedDayOfMonth(day: Int) {
        _uiState.value = _uiState.value?.let {
            _uiState.value?.copy(
                selectedDayOfMonth = day,
                selectedMonth = it.currentVisibleMonth
            )
        }
    }

    fun updateAvailableMonths() {
        _uiState.value?.let {
            _uiState.value = it.copy(availableMonths = Constant.getMonths(it.selectedYear))
        }
    }

    fun updateIsMonthYearViewVisible(visible: Boolean) {
        _uiState.value = _uiState.value?.copy(isMonthYearViewVisible = visible)
    }

    fun updateUiState(uiState: CalendarUiState) {
        _uiState.value = uiState
    }

    fun moveToNextMonth() {
        _uiState.value?.apply {
            if (currentVisibleMonth.number == 11) { // if it is december
                val newYear = selectedYear + 1
                val newMonthList = Constant.getMonths(newYear)
                _uiState.value = _uiState.value?.copy(
                    selectedYear = newYear,
                    availableMonths = newMonthList,
                    currentVisibleMonth = newMonthList[0]
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    currentVisibleMonth = availableMonths[currentVisibleMonth.number + 1]
                )
            }
        }
    }

    fun moveToPreviousMonth() {
        _uiState.value?.apply {
            if (currentVisibleMonth.number == 0) { // if it is december
                val newYear = selectedYear - 1
                val newMonthList = Constant.getMonths(newYear)
                _uiState.value = _uiState.value?.copy(
                    selectedYear = newYear,
                    availableMonths = newMonthList,
                    currentVisibleMonth = newMonthList[11]
                )
            } else {
                _uiState.value = _uiState.value?.copy(
                    currentVisibleMonth = availableMonths[currentVisibleMonth.number - 1]
                )
            }
        }
    }

    fun updateSelectedYearIndex(index: Int) {
        val newMonthList = Constant.getMonths(Constant.years[index])
        _uiState.value = _uiState.value?.copy(
            selectedYearIndex = index,
            selectedYear = Constant.years[index],
            availableMonths = newMonthList,
            currentVisibleMonth = newMonthList[_uiState.value?.currentVisibleMonth?.number ?: 0]
        )
    }
}