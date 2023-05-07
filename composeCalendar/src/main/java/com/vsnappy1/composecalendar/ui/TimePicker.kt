package com.vsnappy1.composecalendar.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vsnappy1.composecalendar.data.model.ComposeTimePickerTime
import com.vsnappy1.composecalendar.data.model.DefaultTime
import com.vsnappy1.composecalendar.ui.model.TimePickerConfiguration
import com.vsnappy1.composecalendar.ui.viewmodel.DatePickerModel

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    onTimeSelected: (Int, Int) -> Unit = { _: Int, _: Int -> },
    time: ComposeTimePickerTime? = null,
    timePickerConfiguration: TimePickerConfiguration = TimePickerConfiguration(),
    viewModel: DatePickerModel = viewModel()
) {
    var _time = time
    if(time == null){
        _time = DefaultTime.getTime(LocalContext.current)
    }
}