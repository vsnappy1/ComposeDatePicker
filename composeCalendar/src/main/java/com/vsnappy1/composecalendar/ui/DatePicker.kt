package com.vsnappy1.composecalendar.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vsnappy1.composecalendar.R
import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.data.model.ComposeDatePickerDate
import com.vsnappy1.composecalendar.data.model.Month
import com.vsnappy1.composecalendar.data.model.SelectionLimiter
import com.vsnappy1.composecalendar.enums.Days
import com.vsnappy1.composecalendar.extension.noRippleClickable
import com.vsnappy1.composecalendar.theme.Size.medium
import com.vsnappy1.composecalendar.theme.Size.small
import com.vsnappy1.composecalendar.ui.component.AnimatedFadeVisibility
import com.vsnappy1.composecalendar.ui.model.DatePickerUiState
import com.vsnappy1.composecalendar.ui.model.DateViewConfiguration
import com.vsnappy1.composecalendar.ui.model.HeaderConfiguration
import com.vsnappy1.composecalendar.ui.model.MonthYearViewConfiguration
import com.vsnappy1.composecalendar.ui.viewmodel.DatePickerModel
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.streams.toList


@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (Int, Int, Int) -> Unit = { _: Int, _: Int, _: Int -> },
    date: ComposeDatePickerDate = ComposeDatePickerDate(),
    selectionLimiter: SelectionLimiter = SelectionLimiter(),
    headerConfiguration: HeaderConfiguration = HeaderConfiguration(),
    dateViewConfiguration: DateViewConfiguration = DateViewConfiguration(),
    monthYearViewConfiguration: MonthYearViewConfiguration = MonthYearViewConfiguration(),
    viewModel: DatePickerModel = viewModel()
) {
    // Key is Unit because I want this to run only once not every time when is composable is recomposed.
    LaunchedEffect(key1 = Unit) {
        viewModel.setDate(date)
    }

    val uiState by viewModel.uiState.observeAsState(
        DatePickerUiState(
            selectedYear = date.year,
            selectedMonth = Constant.getMonths(date.year)[date.month],
            selectedDayOfMonth = date.day
        )
    )
    Box(modifier = modifier) {
        CalendarHeader(
            title = "${uiState.currentVisibleMonth.name} ${uiState.selectedYear}",
            onMonthYearClick = { viewModel.updateUiState(uiState.copy(isMonthYearViewVisible = !uiState.isMonthYearViewVisible)) },
            onNextClick = { viewModel.moveToNextMonth() },
            onPreviousClick = { viewModel.moveToPreviousMonth() },
            isPreviousNextVisible = !uiState.isMonthYearViewVisible,
            configuration = headerConfiguration
        )
        Spacer(modifier = Modifier.height(small))
        Box(
            modifier = Modifier
                .height(dateViewConfiguration.selectedDateBackgroundSize * 8f + small)
                .padding(top = headerConfiguration.height)
        ) {
            AnimatedFadeVisibility(
                visible = uiState.isMonthYearViewVisible
            ) {
                MonthAndYearView(
                    modifier = Modifier.align(Alignment.Center),
                    selectedMonth = uiState.selectedMonthIndex,
                    onMonthChange = { viewModel.updateSelectedMonthIndex(it) },
                    selectedYear = uiState.selectedYearIndex,
                    onYearChange = { viewModel.updateSelectedYearIndex(it) },
                    years = uiState.availableYears.stream().map { it.toString() }.toList(),
                    configuration = monthYearViewConfiguration.copy(height = dateViewConfiguration.selectedDateBackgroundSize * 7)
                )
            }
            AnimatedFadeVisibility(
                visible = !uiState.isMonthYearViewVisible
            ) {
                DateView(
                    currentVisibleMonth = uiState.currentVisibleMonth,
                    selectedYear = uiState.selectedYear,
                    selectedMonth = uiState.selectedMonth,
                    selectedDayOfMonth = uiState.selectedDayOfMonth,
                    selectionLimiter = selectionLimiter,
                    onDaySelected = {
                        viewModel.updateSelectedDayAndMonth(it)
                        uiState.selectedDayOfMonth?.let { day ->
                            onDateSelected(
                                uiState.selectedYear,
                                uiState.selectedMonth.number,
                                day
                            )
                        }
                    },
                    configuration = dateViewConfiguration
                )
            }
        }
    }
}

@Composable
private fun MonthAndYearView(
    modifier: Modifier = Modifier,
    selectedMonth: Int,
    onMonthChange: (Int) -> Unit,
    selectedYear: Int,
    onYearChange: (Int) -> Unit,
    years: List<String>,
    configuration: MonthYearViewConfiguration
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(
                color = configuration.backgroundColor,
                shape = configuration.backgroundShape
            ),
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = medium)
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(small)
                )
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwipeLazyColumn(
                selectedIndex = selectedMonth,
                onSelectedIndexChange = onMonthChange,
                items = Constant.months,
                configuration = configuration
            )
            SwipeLazyColumn(
                selectedIndex = selectedYear,
                onSelectedIndexChange = onYearChange,
                items = years,
                alignment = Alignment.CenterEnd,
                configuration = configuration
            )
        }
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    alignment: Alignment = Alignment.CenterStart,
    configuration: MonthYearViewConfiguration
) {
    val coroutineScope = rememberCoroutineScope()
    var isManualScrolling by remember { mutableStateOf(true) }
    val listState = rememberLazyListState()
    com.vsnappy1.composecalendar.ui.component.SwipeLazyColumn(
        modifier = modifier,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        isManualScrolling = isManualScrolling,
        height = configuration.height,
        numberOfRowsDisplayed = configuration.numberOfRowsDisplayed,
        listState = listState
    ) {
        items(items.size + configuration.numberOfRowsDisplayed - 1) {
            SliderItem(
                value = it,
                selectedIndex = selectedIndex,
                items = items,
                configuration = configuration,
                alignment = alignment,
                onItemClick = { index ->
                    onSelectedIndexChange(index)
                    coroutineScope.launch {
                        isManualScrolling = false
                        onSelectedIndexChange(index)
                        listState.animateScrollToItem(index)
                        isManualScrolling = true
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
    configuration: MonthYearViewConfiguration,
) {
    val gap = configuration.numberOfRowsDisplayed / 2
    val isSelected = value == selectedIndex + gap
    val scale by animateFloatAsState(targetValue = if (isSelected) configuration.scaleFactor else 1f)
    Box(
        modifier = Modifier
            .height(configuration.height / configuration.numberOfRowsDisplayed)
            .width(configuration.width)
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
                    style = if (isSelected) configuration.selectedTextStyle else configuration.unselectedTextStyle
                )
            }
        }
    }
}


@Composable
private fun DateView(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    currentVisibleMonth: Month,
    selectedDayOfMonth: Int?,
    selectionLimiter: SelectionLimiter,
    onDaySelected: (Int) -> Unit,
    selectedMonth: Month,
    configuration: DateViewConfiguration
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        userScrollEnabled = false,
        modifier = modifier.background(
            color = configuration.backgroundColor,
            shape = configuration.backgroundShape
        )
    ) {
        items(Constant.days) {
            DateViewHeaderItem(day = it, configuration = configuration)
        }
        val count =
            currentVisibleMonth.numberOfDays + currentVisibleMonth.firstDayOfMonth.number - 1
        val topPaddingForItem =
            getTopPaddingForItem(count, configuration.selectedDateBackgroundSize)
        items(count) {
            if (it < currentVisibleMonth.firstDayOfMonth.number - 1) return@items // to create empty boxes
            DateViewBodyItem(
                value = it,
                currentVisibleMonth = currentVisibleMonth,
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDayOfMonth = selectedDayOfMonth,
                selectionLimiter = selectionLimiter,
                onDaySelected = onDaySelected,
                topPaddingForItem = topPaddingForItem,
                configuration = configuration,
            )
        }
    }
}

@Composable
private fun DateViewBodyItem(
    value: Int,
    currentVisibleMonth: Month,
    selectedYear: Int,
    selectedMonth: Month,
    selectedDayOfMonth: Int?,
    selectionLimiter: SelectionLimiter,
    onDaySelected: (Int) -> Unit,
    topPaddingForItem: Dp,
    configuration: DateViewConfiguration,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        val day = value - currentVisibleMonth.firstDayOfMonth.number + 2
        val isSelected = day == selectedDayOfMonth && selectedMonth == currentVisibleMonth
        val isWithinRange = selectionLimiter.isWithinRange(
            ComposeDatePickerDate(
                selectedYear,
                currentVisibleMonth.number,
                day
            )
        )
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .padding(top = if (value < 7) 0.dp else topPaddingForItem) // I don't want first row to have any padding
                .size(configuration.selectedDateBackgroundSize)
                .clip(configuration.selectedDateBackgroundShape)
                .clickable(enabled = isWithinRange) { onDaySelected(day) }
                .background(if (isSelected) configuration.selectedDateBackgroundColor else Color.Transparent)
        ) {
            Text(
                text = "$day",
                textAlign = TextAlign.Center,
                style = if (isSelected) configuration.selectedDateStyle.copy(color = if (isWithinRange) configuration.selectedDateStyle.color else configuration.disabledDateColor)
                else configuration.unselectedDateStyle.copy(
                    color = if (isWithinRange) {
                        if (value % 7 == 0) configuration.sundayTextColor
                        else configuration.unselectedDateStyle.color
                    } else {
                        configuration.disabledDateColor
                    }
                ),
            )
        }
    }
}

@Composable
private fun DateViewHeaderItem(
    configuration: DateViewConfiguration,
    day: Days
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .size(configuration.selectedDateBackgroundSize)
    ) {
        Text(
            text = day.abbreviation,
            textAlign = TextAlign.Center,
            style = configuration.headerTextStyle.copy(
                color = if (day.number == 1) configuration.sundayTextColor else configuration.headerTextStyle.color
            ),
        )
    }
}

// Not every month has same number of weeks, so to maintain the same size for calender we add padding if there are less weeks
private fun getTopPaddingForItem(
    count: Int,
    itemSize: Dp
): Dp {
    val numberOfRowsVisible = ceil(count.toDouble() / 7)
    val remainingRows: Int = 6 - numberOfRowsVisible.toInt()
    return (itemSize * remainingRows) / (numberOfRowsVisible.toInt() - 1)
}

@Composable
private fun CalendarHeader(
    modifier: Modifier = Modifier,
    title: String,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onMonthYearClick: () -> Unit,
    isPreviousNextVisible: Boolean,
    configuration: HeaderConfiguration
) {
    Box(
        modifier = Modifier
            .background(
                color = configuration.backgroundColor,
                shape = configuration.backgroundShape
            )
            .fillMaxWidth()
            .height(configuration.height)
    ) {
        Text(
            text = title,
            style = configuration.textStyle,
            modifier = modifier
                .padding(start = medium)
                .noRippleClickable { onMonthYearClick() }
                .align(Alignment.CenterStart),
        )

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            AnimatedFadeVisibility(visible = isPreviousNextVisible) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.leftArrow),
                    tint = configuration.arrowColor,
                    modifier = Modifier
                        .size(configuration.arrowSize)
                        .noRippleClickable { onPreviousClick() }
                )
            }
            Spacer(modifier = Modifier.width(medium))
            AnimatedFadeVisibility(visible = isPreviousNextVisible) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.leftArrow),
                    tint = configuration.arrowColor,
                    modifier = Modifier
                        .size(configuration.arrowSize)
                        .noRippleClickable { onNextClick() }
                )
            }
        }
    }
}


@Preview
@Composable
fun DefaultCalendar() {
    DatePicker()
}