package com.vsnappy1.timepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsnappy1.timepicker.data.ComposeTimePickerTime
import com.vsnappy1.timepicker.enums.TimeOfDay
import com.vsnappy1.timepicker.ui.model.TimePickerUiState

class TimePickerViewModel : ViewModel() {

    private val _uiState: MutableLiveData<TimePickerUiState> = MutableLiveData(TimePickerUiState())
    val uiState: LiveData<TimePickerUiState> = _uiState
    fun updateSelectedHourIndex(index: Int) {
        _uiState.value = _uiState.value?.copy(selectedHourIndex = index)
    }

    fun updateSelectedMinuteIndex(index: Int) {
        _uiState.value = _uiState.value?.copy(selectedMinuteIndex = index)
    }

    fun updateSelectedTimeOfDayIndex(index: Int) {
        _uiState.value = _uiState.value?.copy(selectedTimeOfDayIndex = index)
    }

    fun getSelectedTime(): ComposeTimePickerTime? {
        val time = _uiState.value?.let {
            if (it.is24Hour) {
                ComposeTimePickerTime.TwentyFourHourTime(
                    it.hours[it.selectedHourIndex].toInt(),
                    it.minutes[it.selectedMinuteIndex].toInt()
                )
            } else {
                ComposeTimePickerTime.TwelveHourTime(
                    it.hours[it.selectedHourIndex].toInt(),
                    it.minutes[it.selectedMinuteIndex].toInt(),
                    if (it.timesOfDay[it.selectedTimeOfDayIndex] == "AM") TimeOfDay.AM else TimeOfDay.PM
                )
            }
        }
        return time
    }

    fun updateUiState(uiState: TimePickerUiState) {
        _uiState.value = uiState
    }


}