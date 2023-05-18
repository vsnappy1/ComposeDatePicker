package com.vsnappy1.timepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsnappy1.timepicker.data.Constant
import com.vsnappy1.timepicker.data.model.ComposeTimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import com.vsnappy1.timepicker.ui.model.TimePickerUiState

internal class TimePickerViewModel : ViewModel() {

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

    fun updateUiState(
        timePickerTime: ComposeTimePickerTime,
        minuteGap: MinuteGap
    ) {
        _uiState.value = getUiStateTimeProvided(timePickerTime, minuteGap)
    }

    fun getUiStateTimeProvided(
        timePickerTime: ComposeTimePickerTime,
        minuteGap: MinuteGap
    ): TimePickerUiState {
        var hour = 0
        var minute = 0
        var timeOfDay = TimeOfDay.PM
        if (timePickerTime is ComposeTimePickerTime.TwentyFourHourTime) {
            if (timePickerTime.hour < 0 || timePickerTime.hour > 23) {
                throw IllegalArgumentException("Invalid hour: ${timePickerTime.hour}, The hour value must be between 0 and 23 inclusive.")
            }
            if (timePickerTime.minute < 0 || timePickerTime.minute > 59) {
                throw IllegalArgumentException("Invalid minute: ${timePickerTime.minute}, The minute value must be between 0 and 59 inclusive.")
            }
            if (timePickerTime.minute % minuteGap.gap != 0) {
                throw IllegalArgumentException("Invalid minute: ${timePickerTime.minute}, Since minute gap is ${minuteGap.gap} the minute value must be multiple of ${minuteGap.gap} & between 0 and 59 inclusive. If you want some other value (e.g. 1,2,3....59) please change minuteGap attribute to MinuteGap.ONE.")
            }
            hour = timePickerTime.hour
            minute = timePickerTime.minute

        } else if (timePickerTime is ComposeTimePickerTime.TwelveHourTime) {
            if (timePickerTime.hour < 1 || timePickerTime.hour > 12) {
                throw IllegalArgumentException("Invalid hour: ${timePickerTime.hour}, The hour value must be between 1 and 12 inclusive.")
            }
            if (timePickerTime.minute < 0 || timePickerTime.minute > 59) {
                throw IllegalArgumentException("Invalid minute: ${timePickerTime.minute}, The minute value must be between 0 and 59 inclusive.")
            }
            if (timePickerTime.minute % minuteGap.gap != 0) {
                throw IllegalArgumentException("Invalid minute: ${timePickerTime.minute}, Since minute gap is ${minuteGap.gap} the minute value must be multiple of ${minuteGap.gap} & between 0 and 59 inclusive. If you want some other value (e.g. 1,2,3....59) please change minuteGap attribute to MinuteGap.ONE.")
            }
            hour = timePickerTime.hour
            minute = timePickerTime.minute
            timeOfDay = timePickerTime.timeOfDay
        }

        val is24 = timePickerTime is ComposeTimePickerTime.TwentyFourHourTime
        return TimePickerUiState(
            is24Hour = is24,
            minuteGap = minuteGap,
            hours = Constant.getHours(is24),
            selectedHourIndex = Constant.getMiddleOfHour(is24) + hour,
            minutes = Constant.getMinutes(minuteGap),
            selectedMinuteIndex = Constant.getMiddleOfMinute(minuteGap) + minute / minuteGap.gap,
            timesOfDay = Constant.getTimesOfDay(),
            selectedTimeOfDayIndex = timeOfDay.value
        )
    }
}