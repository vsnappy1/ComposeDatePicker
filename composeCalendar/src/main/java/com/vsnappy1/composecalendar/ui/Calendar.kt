package com.vsnappy1.composecalendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.vsnappy1.composecalendar.enums.Month
import com.vsnappy1.composecalendar.extension.noRippleClickable
import com.vsnappy1.composecalendar.theme.Size.medium
import com.vsnappy1.composecalendar.theme.Size.small
import com.vsnappy1.composecalendar.ui.model.CalendarUiState
import com.vsnappy1.composecalendar.ui.model.Date
import com.vsnappy1.composecalendar.ui.model.DateViewConfiguration
import com.vsnappy1.composecalendar.ui.model.DefaultDate
import com.vsnappy1.composecalendar.ui.model.HeaderConfiguration
import com.vsnappy1.composecalendar.ui.model.MonthYearViewConfiguration
import com.vsnappy1.composecalendar.ui.viewmodel.CalendarViewModel
import kotlin.math.ceil
import kotlin.streams.toList


@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    onDateSelected: (Int, Int, Int) -> Unit = { year: Int, month: Int, day: Int -> },
    date: Date = DefaultDate.defaultDate,
    headerConfiguration: HeaderConfiguration = HeaderConfiguration(),
    dateViewConfiguration: DateViewConfiguration = DateViewConfiguration(),
    monthYearViewConfiguration: MonthYearViewConfiguration = MonthYearViewConfiguration(),
    viewModel: CalendarViewModel = viewModel()
) {
    // Key is Unit because I want this to run only once not every time when is composable is recomposed.
    LaunchedEffect(key1 = Unit) {
        viewModel.setDate(date)
    }

    val uiState by viewModel.uiState.observeAsState(
        CalendarUiState(
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
                .height(dateViewConfiguration.selectedDateBackgroundSize * 8f + medium)
                .padding(top = headerConfiguration.height)
        ) {
            AnimatedFadeVisibility(
                visible = !uiState.isMonthYearViewVisible
            ) {
                DateView(
                    currentVisibleMonth = uiState.currentVisibleMonth,
                    selectedMonth = uiState.selectedMonth,
                    selectedDayOfMonth = uiState.selectedDayOfMonth,
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
            AnimatedFadeVisibility(
                visible = uiState.isMonthYearViewVisible
            ) {
                MonthAndYearView(
                    modifier = Modifier.align(Alignment.Center),
                    selectedMonth = uiState.currentVisibleMonth.number,
                    onMonthChange = { viewModel.updateCurrentVisibleMonth(it) },
                    months = uiState.availableMonths.stream().map { it.name }.toList(),
                    selectedYear = uiState.selectedYearIndex,
                    onYearChange = { viewModel.updateSelectedYearIndex(it) },
                    years = uiState.availableYears.stream().map { it.toString() }.toList(),
                    configuration = monthYearViewConfiguration
                )
            }
        }
    }
}

@Composable
fun AnimatedFadeVisibility(
    visible: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        content()
    }
}

@Composable
private fun MonthAndYearView(
    modifier: Modifier = Modifier,
    selectedMonth: Int,
    onMonthChange: (Int) -> Unit,
    months: List<String>,
    selectedYear: Int,
    onYearChange: (Int) -> Unit,
    years: List<String>,
    configuration: MonthYearViewConfiguration
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = configuration.backgroundColor,
                shape = configuration.backgroundShape
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SwipeLazyColumn(
            selectedIndex = selectedMonth,
            onSelectedIndexChange = onMonthChange,
            items = months,
            configuration = configuration
        )
        SwipeLazyColumn(
            selectedIndex = selectedYear,
            onSelectedIndexChange = onYearChange,
            items = years,
            configuration = configuration
        )
    }
}

@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    configuration: MonthYearViewConfiguration
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        listState.scrollToItem(selectedIndex)
    }

    if (listState.isScrollInProgress) {
        LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
            onSelectedIndexChange(listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > configuration.height.value / configuration.numberOfRowsDisplayed) 1 else 0)
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LazyColumn(
        modifier = modifier
            .height(configuration.height)
            .width(configuration.width),
        state = listState
    ) {
        items(items.size + configuration.numberOfRowsDisplayed - 1) {
            val index = configuration.numberOfRowsDisplayed / 2
            val isSelected = it == selectedIndex + index
            val scale by animateFloatAsState(targetValue = if (isSelected) configuration.scaleFactor else 1f)
            Box(
                modifier = Modifier
                    .height(configuration.height / configuration.numberOfRowsDisplayed)
                    .width(configuration.width)
            ) {
                if (it >= index && it < items.size + index) {
                    Text(
                        text = items[it - index],
                        modifier = Modifier
                            .align(Alignment.Center)
                            .scale(scale),
                        textAlign = TextAlign.Center,
                        style = if (isSelected) configuration.selectedTextStyle else configuration.unselectedTextStyle
                    )
                }
            }
        }
    }
}


@Composable
private fun DateView(
    modifier: Modifier = Modifier,
    currentVisibleMonth: Month,
    selectedDayOfMonth: Int?,
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
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .size(configuration.selectedDateBackgroundSize)
            ) {
                Text(
                    text = it.abbreviation,
                    textAlign = TextAlign.Center,
                    style = configuration.headerTextStyle.copy(
                        color = if (it.number == 1) configuration.sundayTextColor else configuration.headerTextStyle.color
                    ),
                )
            }
        }
        val count =
            currentVisibleMonth.numberOfDays + currentVisibleMonth.firstDayOfMonth.number - 1
        val topPaddingForItem =
            getTopPaddingForItem(count, configuration.selectedDateBackgroundSize)
        items(count) {
            if (it < currentVisibleMonth.firstDayOfMonth.number - 1) return@items // to create empty boxes
            Box(
                contentAlignment = Alignment.Center
            ) {
                val day = it - currentVisibleMonth.firstDayOfMonth.number + 2
                val isSelected = day == selectedDayOfMonth && selectedMonth == currentVisibleMonth
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .padding(top = if (it < 7) 0.dp else topPaddingForItem) // I don't want first row to have any padding
                        .size(configuration.selectedDateBackgroundSize)
                        .clip(configuration.selectedDateBackgroundShape)
                        .clickable { onDaySelected(day) }
                        .background(if (isSelected) configuration.selectedDateBackgroundColor else Color.Transparent)
                ) {
                    Text(
                        text = "$day",
                        textAlign = TextAlign.Center,
                        style = if (isSelected) configuration.selectedDateStyle else configuration.unselectedDateStyle.copy(
                            color = if (it % 7 == 0) configuration.sundayTextColor else configuration.unselectedDateStyle.color
                        ),
                    )
                }
            }
        }
    }
}

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
    Calendar()
}