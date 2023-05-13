package com.vsnappy1.datepicker

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import com.vsnappy1.component.AnimatedFadeVisibility
import com.vsnappy1.composedatepicker.R
import com.vsnappy1.datepicker.data.Constant
import com.vsnappy1.datepicker.data.model.ComposeDatePickerDate
import com.vsnappy1.datepicker.data.model.DefaultDate
import com.vsnappy1.datepicker.data.model.Month
import com.vsnappy1.datepicker.data.model.SelectionLimiter
import com.vsnappy1.datepicker.enums.Days
import com.vsnappy1.datepicker.extension.noRippleClickable
import com.vsnappy1.datepicker.ui.model.DatePickerUiState
import com.vsnappy1.datepicker.ui.model.DateViewConfiguration
import com.vsnappy1.datepicker.ui.model.HeaderConfiguration
import com.vsnappy1.datepicker.ui.model.MonthYearViewConfiguration
import com.vsnappy1.datepicker.ui.viewmodel.DatePickerViewModel
import com.vsnappy1.theme.Size.medium
import com.vsnappy1.theme.Size.small
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (Int, Int, Int) -> Unit,
    date: ComposeDatePickerDate = DefaultDate.defaultDate,
    selectionLimiter: SelectionLimiter = SelectionLimiter(),
    headerConfiguration: HeaderConfiguration = HeaderConfiguration(),
    dateViewConfiguration: DateViewConfiguration = DateViewConfiguration(),
    monthYearViewConfiguration: MonthYearViewConfiguration = MonthYearViewConfiguration()
) {
    val viewModel: DatePickerViewModel = viewModel()

    // there may be 4 to 6 weeks in a month plus we have header that also makes an row
    val totalRows = 8f

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
        // TODO add sliding effect when next or previous arrow is pressed
        CalendarHeader(
            title = "${uiState.currentVisibleMonth.name} ${uiState.selectedYear}",
            onMonthYearClick = { viewModel.toggleIsMonthYearViewVisible() },
            onNextClick = { viewModel.moveToNextMonth() },
            onPreviousClick = { viewModel.moveToPreviousMonth() },
            isPreviousNextVisible = !uiState.isMonthYearViewVisible,
            configuration = headerConfiguration,
            themeColor = dateViewConfiguration.selectedDateBackgroundColor
        )
        Spacer(modifier = Modifier.height(small))
        Box(
            modifier = Modifier
                .height(dateViewConfiguration.selectedDateBackgroundSize * totalRows + small)
                .padding(top = headerConfiguration.height)
        ) {
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
                        onDateSelected(
                            uiState.selectedYear,
                            uiState.selectedMonth.number,
                            uiState.selectedDayOfMonth
                        )
                    },
                    configuration = dateViewConfiguration
                )
            }
            AnimatedFadeVisibility(
                visible = uiState.isMonthYearViewVisible
            ) {
                MonthAndYearView(
                    modifier = Modifier.align(Alignment.Center),
                    selectedMonth = uiState.selectedMonthIndex,
                    onMonthChange = { viewModel.updateSelectedMonthIndex(it) },
                    selectedYear = uiState.selectedYearIndex,
                    onYearChange = { viewModel.updateSelectedYearIndex(it) },
                    years = uiState.years,
                    months = uiState.months,
                    configuration = monthYearViewConfiguration.copy(height = dateViewConfiguration.selectedDateBackgroundSize * 7)
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
    months: List<String>,
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
                .height(configuration.selectedAreaHeight)
                .background(
                    color = configuration.selectedAreaColor,
                    shape = configuration.selectedAreaShape
                )
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwipeLazyColumn(
                modifier = Modifier.weight(0.5f),
                selectedIndex = selectedMonth,
                onSelectedIndexChange = onMonthChange,
                items = months,
                configuration = configuration
            )
            SwipeLazyColumn(
                modifier = Modifier.weight(0.5f),
                selectedIndex = selectedYear,
                onSelectedIndexChange = onYearChange,
                items = years,
                alignment = Alignment.CenterEnd,
                configuration = configuration
            )
        }
    }
}

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
    var isAutoScrolling by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    com.vsnappy1.component.SwipeLazyColumn(
        modifier = modifier,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        isAutoScrolling = isAutoScrolling,
        height = configuration.height,
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
    configuration: MonthYearViewConfiguration,
) {
    // this gap variable helps in maintaining list as center focused list
    val gap = configuration.numberOfRowsDisplayed / 2
    val isSelected = value == selectedIndex + gap
    val scale by animateFloatAsState(targetValue = if (isSelected) configuration.scaleFactor else 1f)
    Box(
        modifier = Modifier
            .height(configuration.height / configuration.numberOfRowsDisplayed)
            .padding(
                start = if (alignment == Alignment.CenterStart) configuration.width else 0.dp,
                end = if (alignment == Alignment.CenterEnd) configuration.width else 0.dp
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
        // since we may need few empty cells because every month starts with a different day(Monday, Tuesday, ..)
        // that's way we add some number X into the count
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
                .noRippleClickable(enabled = isWithinRange) { onDaySelected(day) }
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
    configuration: HeaderConfiguration,
    themeColor: Color
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
        val textColor by
        animateColorAsState(
            targetValue = if (isPreviousNextVisible) configuration.textStyle.color else themeColor,
            animationSpec = tween(durationMillis = 400, delayMillis = 100)
        )
        Text(
            text = title,
            style = configuration.textStyle.copy(color = textColor),
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
fun DefaultDatePicker() {
    DatePicker(onDateSelected = { _: Int, _: Int, _: Int -> })
}