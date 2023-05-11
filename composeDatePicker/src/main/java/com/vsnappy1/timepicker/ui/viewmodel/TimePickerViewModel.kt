package com.vsnappy1.timepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsnappy1.timepicker.data.ComposeTimePickerTime
import com.vsnappy1.timepicker.ui.model.TimePickerUiState

class TimePickerViewModel : ViewModel() {

    private val _uiState: MutableLiveData<TimePickerUiState> = MutableLiveData(TimePickerUiState())
    val uiState: LiveData<TimePickerUiState> = _uiState

}