package com.vsnappy1.composecalendar.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsnappy1.composecalendar.ui.model.TimePickerUiState

class TimePickerViewModel : ViewModel() {


    private val _uiState: MutableLiveData<TimePickerUiState> = MutableLiveData()
}