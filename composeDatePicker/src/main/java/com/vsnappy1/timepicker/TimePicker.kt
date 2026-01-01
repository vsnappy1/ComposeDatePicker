package com.vsnappy1.timepicker

import android.text.format.DateFormat
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
import com.vsnappy1.timepicker.data.model.TimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.ui.model.TimePickerConfiguration
import com.vsnappy1.timepicker.ui.viewmodel.TimePickerViewModel
import kotlinx.coroutines.launch

/**
 * A Jetpack Compose function that provides a customizable time picker.
 *
 * This time picker allows the user to select a time by scrolling through hours, minutes, and
 * AM/PM (if not in 24-hour format). The selected time is reported through the `onTimeSelected` callback.
 *
 * Example usage:
 * ```
 * TimePicker(
 *     modifier = Modifier.height(200.dp),
 *     onTimeSelected = { hour, minute ->
 *         // Handle the selected time
 *     },
 *     time = TimePickerTime(hour = 10, minute = 30),
 *     is24Hour = false
 * )
 * ```
 *
 * @param modifier The modifier to be applied to the time picker.
 * @param onTimeSelected A callback that is invoked when the user stops scrolling on a new time.
 *                       It provides the selected hour (0-23) and minute.
 * @param is24Hour An optional boolean to force the time picker into 24-hour or 12-hour format.
 *                 If null, it defaults to the system's setting.
 * @param minuteGap The interval between minutes in the minute's column. Defaults to `MinuteGap.ONE`.
 *                  Other options include `MinuteGap.FIVE`, `MinuteGap.TEN`, etc.
 * @param time An optional `TimePickerTime` object to set the initial time displayed.
 *             If null, the current system time is used.
 * @param configuration An optional `TimePickerConfiguration` to customize the appearance of the
 *                      time picker (e.g., text styles, colors, scaling).
 * @param id An optional integer to create a unique key for the `ViewModel`. This is useful when
 *           you have multiple `TimePicker` instances on the same screen.
 */
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    onTimeSelected: (Int, Int) -> Unit,
    is24Hour: Boolean? = null,
    minuteGap: MinuteGap = MinuteGap.ONE,
    time: TimePickerTime? = null,
    configuration: TimePickerConfiguration = TimePickerConfiguration.Builder().build(),
    id: Int = 1
) {
    val viewModel: TimePickerViewModel = viewModel(key = "TimePickerViewModel$id")
    val timePickerTime = time ?: TimePickerTime.getTime()
    val is24: Boolean = is24Hour ?: DateFormat.is24HourFormat(LocalContext.current)
    val timePickerUiState = viewModel.getUiStateTimeProvided(timePickerTime, minuteGap, is24)
    val uiState by viewModel.uiState.observeAsState(timePickerUiState)
    LaunchedEffect(key1 = Unit) { viewModel.updateUiState(timePickerTime, minuteGap, is24) }

    TimePickerView(
        modifier = modifier,
        hours = uiState.hours,
        selectedHourIndex = uiState.selectedHourIndex,
        onSelectedHourIndexChange = {
            viewModel.updateSelectedHourIndex(it)
        },
        minutes = uiState.minutes,
        selectedMinuteIndex = uiState.selectedMinuteIndex,
        onSelectedMinuteIndexChange = {
            viewModel.updateSelectedMinuteIndex(it)
        },
        timesOfDay = uiState.timesOfDay,
        selectedTimeOfDayIndex = uiState.selectedTimeOfDayIndex,
        onSelectedTimeOfDayIndexChange = {
            viewModel.updateSelectedTimeOfDayIndex(it)
        },
        is24Hour = uiState.is24Hour,
        configuration = configuration,
        onScrollingStopped = {
            viewModel.getSelectedTime()?.apply {
                onTimeSelected(hour, minute)
            }
        }
    )
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
    onScrollingStopped: () -> Unit,
) {
    var height by remember { mutableStateOf(configuration.height) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onGloballyPositioned {
                if (it.size.height == 0) return@onGloballyPositioned
                height = it.size.height.toDp() // Update the height
            },
    ) {
        Box(modifier = Modifier.fillMaxWidth())
        Box(
            modifier = Modifier
                .padding(horizontal = medium)
                .fillMaxWidth()
                .height(configuration.selectedTimeAreaHeight)
                .background(
                    color = configuration.selectedTimeAreaColor,
                    shape = configuration.selectedTimeAreaShape
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
                height = height,
                onScrollingStopped = onScrollingStopped
            )
            SwipeLazyColumn(
                modifier = Modifier.weight(if (is24Hour) 0.5f else 0.2f),
                selectedIndex = selectedMinuteIndex,
                onSelectedIndexChange = onSelectedMinuteIndexChange,
                items = minutes,
                textAlign = if (is24Hour) TextAlign.Start else TextAlign.Center,
                alignment = if (is24Hour) Alignment.CenterStart else Alignment.Center,
                configuration = configuration,
                height = height,
                onScrollingStopped = onScrollingStopped
            )
            if (!is24Hour) {
                SwipeLazyColumn(
                    modifier = Modifier.weight(0.4f),
                    selectedIndex = selectedTimeOfDayIndex,
                    onSelectedIndexChange = onSelectedTimeOfDayIndexChange,
                    items = timesOfDay,
                    alignment = Alignment.CenterStart,
                    configuration = configuration,
                    height = height,
                    isScrollingToSelectedItemEnabled = true,
                    onScrollingStopped = onScrollingStopped
                )
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
    isScrollingToSelectedItemEnabled: Boolean = false,
    height: Dp,
    onScrollingStopped: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isAutoScrolling by remember { mutableStateOf(false) }
    val listState = rememberLazyListState(selectedIndex)
    com.vsnappy1.component.SwipeLazyColumn(
        modifier = modifier,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        isAutoScrolling = isAutoScrolling,
        height = height,
        isScrollingToSelectedItemEnabled = isScrollingToSelectedItemEnabled,
        numberOfRowsDisplayed = configuration.numberOfTimeRowsDisplayed,
        listState = listState,
        onScrollingStopped = {
            isAutoScrolling = false
            onScrollingStopped()
        }
    ) {
        // we add some empty rows at the beginning and end of list to make it feel that it is a center focused list
        val count = items.size + configuration.numberOfTimeRowsDisplayed - 1
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
                    coroutineScope.launch {
                        isAutoScrolling = true
                        onSelectedIndexChange(index)
                        listState.animateScrollToItem(index)
                        isAutoScrolling = false
                        onScrollingStopped()
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
    val gap = configuration.numberOfTimeRowsDisplayed / 2
    val isSelected = value == selectedIndex + gap
    val scale by animateFloatAsState(targetValue = if (isSelected) configuration.selectedTimeScaleFactor else 1f)
    Box(
        modifier = Modifier
            .height(height / configuration.numberOfTimeRowsDisplayed)
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
                    style = if (isSelected) configuration.selectedTimeTextStyle else configuration.timeTextStyle,
                    textAlign = textAlign
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTimePicker() {
    TimePicker(onTimeSelected = { _: Int, _: Int -> })
}