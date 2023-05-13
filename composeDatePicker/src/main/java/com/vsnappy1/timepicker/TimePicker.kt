package com.vsnappy1.timepicker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vsnappy1.extension.noRippleClickable
import com.vsnappy1.extension.toDp
import com.vsnappy1.theme.Size.extraLarge
import com.vsnappy1.theme.Size.medium
import com.vsnappy1.timepicker.data.model.ComposeTimePickerTime
import com.vsnappy1.timepicker.data.model.DefaultTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import com.vsnappy1.timepicker.ui.model.TimePickerConfiguration
import com.vsnappy1.timepicker.ui.viewmodel.TimePickerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    onTimeSelected: (Int, Int, TimeOfDay?) -> Unit,
    is24Hour: Boolean? = null,
    minuteGap: MinuteGap = MinuteGap.FIVE,
    time: ComposeTimePickerTime? = null, // This has more priority in terms of is24Hour
    timePickerConfiguration: TimePickerConfiguration = TimePickerConfiguration(),
) {
    val viewModel: TimePickerViewModel = viewModel()
    val timePickerTime = time ?: DefaultTime.getTime(LocalContext.current, minuteGap, is24Hour)
    val timePickerUiState = viewModel.getUiStateTimeProvided(timePickerTime, minuteGap)
    LaunchedEffect(key1 = Unit) { viewModel.updateUiState(timePickerTime, minuteGap) }
    val uiState by viewModel.uiState.observeAsState(timePickerUiState)

    TimePickerView(
        modifier = modifier,
        hours = uiState.hours,
        selectedHourIndex = uiState.selectedHourIndex,
        onSelectedHourIndexChange = {
            viewModel.updateSelectedHourIndex(it)
            viewModel.getSelectedTime()?.trigger(onTimeSelected)
        },
        minutes = uiState.minutes,
        selectedMinuteIndex = uiState.selectedMinuteIndex,
        onSelectedMinuteIndexChange = {
            viewModel.updateSelectedMinuteIndex(it)
            viewModel.getSelectedTime()?.trigger(onTimeSelected)
        },
        timesOfDay = uiState.timesOfDay,
        selectedTimeOfDayIndex = uiState.selectedTimeOfDayIndex,
        onSelectedTimeOfDayIndexChange = {
            viewModel.updateSelectedTimeOfDayIndex(it)
            viewModel.getSelectedTime()?.trigger(onTimeSelected)
        },
        is24Hour = uiState.is24Hour,
        configuration = timePickerConfiguration
    )
}

private fun ComposeTimePickerTime.trigger(onTimeSelected: (Int, Int, TimeOfDay?) -> Unit) {
    if (this is ComposeTimePickerTime.TwentyFourHourTime) {
        onTimeSelected(hour, minute, null)
    } else if (this is ComposeTimePickerTime.TwelveHourTime) {
        onTimeSelected(hour, minute, timeOfDay)
    }
}

@Composable
private fun TimePickerView(
    modifier: Modifier = Modifier,
    hours: List<String>,
    selectedHourIndex: Int,
    onSelectedHourIndexChange: (Int) -> Unit,
    minutes: List<String>,
    selectedMinuteIndex: Int,
    onSelectedMinuteIndexChange: (Int) -> Unit,
    timesOfDay: List<String>,
    selectedTimeOfDayIndex: Int,
    onSelectedTimeOfDayIndexChange: (Int) -> Unit,
    is24Hour: Boolean,
    configuration: TimePickerConfiguration,
) {
    var height by remember { mutableStateOf(configuration.height) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {// So that view gets some time to redraw based on given height
        delay(10)
        visible = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onGloballyPositioned {
                if (it.size.height == 0) return@onGloballyPositioned
                height = it.size.height.toDp() // Update the height
            },
    ) {
        Box(modifier = Modifier.fillMaxWidth())
        if (visible) {
            Box(
                modifier = Modifier
                    .padding(horizontal = medium)
                    .fillMaxWidth()
                    .height(configuration.selectedAreaHeight)
                    .background(
                        color = configuration.selectedAreaColor,
                        shape = configuration.selectedAreaShape
                    )
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SwipeLazyColumn(
                    modifier = Modifier.weight(if (is24Hour) 0.5f else 0.4f),
                    selectedIndex = selectedHourIndex,
                    onSelectedIndexChange = onSelectedHourIndexChange,
                    items = hours,
                    alignment = Alignment.CenterEnd,
                    configuration = configuration,
                    height = height
                )
                SwipeLazyColumn(
                    modifier = Modifier.weight(if (is24Hour) 0.5f else 0.2f),
                    selectedIndex = selectedMinuteIndex,
                    onSelectedIndexChange = onSelectedMinuteIndexChange,
                    items = minutes,
                    textAlign = if (is24Hour) TextAlign.Start else TextAlign.Center,
                    alignment = if (is24Hour) Alignment.CenterStart else Alignment.Center,
                    configuration = configuration,
                    height = height
                )
                if (!is24Hour) {
                    SwipeLazyColumn(
                        modifier = Modifier.weight(0.4f),
                        selectedIndex = selectedTimeOfDayIndex,
                        onSelectedIndexChange = onSelectedTimeOfDayIndexChange,
                        items = timesOfDay,
                        alignment = Alignment.CenterStart,
                        configuration = configuration,
                        height = height
                    )
                }
            }
        }
    }
}

@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    textAlign: TextAlign = TextAlign.End,
    alignment: Alignment = Alignment.CenterStart,
    configuration: TimePickerConfiguration,
    height: Dp
) {
    val coroutineScope = rememberCoroutineScope()
    var isAutoScrolling by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    com.vsnappy1.component.SwipeLazyColumn(
        modifier = modifier,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        isAutoScrolling = isAutoScrolling,
        height = height,
        numberOfRowsDisplayed = configuration.numberOfRowsDisplayed,
        listState = listState
    ) {
        // we add some empty rows at the beginning and end of list to make it feel that it is a center focused list
        val count = items.size + configuration.numberOfRowsDisplayed - 1
        items(count) {
            SliderItem(
                value = it,
                selectedIndex = selectedIndex,
                items = items,
                configuration = configuration,
                alignment = alignment,
                textAlign = textAlign,
                height = height,
                onItemClick = { index ->
                    onSelectedIndexChange(index)
                    coroutineScope.launch {
                        isAutoScrolling = true
                        onSelectedIndexChange(index)
                        listState.animateScrollToItem(index)
                        isAutoScrolling = false
                    }
                }
            )
        }
    }
}

@Composable
private fun SliderItem(
    value: Int,
    selectedIndex: Int,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    alignment: Alignment,
    configuration: TimePickerConfiguration,
    height: Dp,
    textAlign: TextAlign,
) {
    // this gap variable helps in maintaining list as center focused list
    val gap = configuration.numberOfRowsDisplayed / 2
    val isSelected = value == selectedIndex + gap
    val scale by animateFloatAsState(targetValue = if (isSelected) configuration.scaleFactor else 1f)
    Box(
        modifier = Modifier
            .height(height / configuration.numberOfRowsDisplayed)
            .padding(
                start = if (alignment == Alignment.CenterStart) extraLarge else 0.dp,
                end = if (alignment == Alignment.CenterEnd) extraLarge else 0.dp
            )
    ) {
        if (value >= gap && value < items.size + gap) {
            Box(modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    onItemClick(value - gap)
                }) {
                Text(
                    text = items[value - gap],
                    modifier = Modifier
                        .align(alignment)
                        .scale(scale),
                    style = if (isSelected) configuration.selectedTextStyle else configuration.unselectedTextStyle,
                    textAlign = textAlign
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTimePicker() {
    TimePicker(onTimeSelected = { _: Int, _: Int, _: TimeOfDay? -> })
}