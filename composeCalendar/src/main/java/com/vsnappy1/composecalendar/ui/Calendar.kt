package com.vsnappy1.composecalendar.ui

import android.util.Log
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vsnappy1.composecalendar.R
import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.enums.Month
import com.vsnappy1.composecalendar.extension.noRippleClickable
import com.vsnappy1.composecalendar.theme.extraLarge
import com.vsnappy1.composecalendar.theme.medium
import com.vsnappy1.composecalendar.theme.small
import com.vsnappy1.composecalendar.ui.model.CalendarUiState
import com.vsnappy1.composecalendar.ui.viewmodel.CalendarViewModel
import kotlin.streams.toList


@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.observeAsState(CalendarUiState())
    Box(modifier = modifier.padding(0.dp)) {
        Column {
            HeaderMonth(
                title = "${uiState.currentVisibleMonth.name} ${uiState.selectedYear}",
                onMonthYearClick = { viewModel.updateUiState(uiState.copy(isMonthYearViewVisible = !uiState.isMonthYearViewVisible)) },
                onNextClick = { viewModel.moveToNextMonth() },
                onPreviousClick = { viewModel.moveToPreviousMonth() },
                isPreviousNextVisible = !uiState.isMonthYearViewVisible
            )
            Spacer(modifier = Modifier.height(small))
            Box {
                AnimatedFadeVisibility(
                    visible = !uiState.isMonthYearViewVisible
                ) {
                    DateView(
                        currentVisibleMonth = uiState.currentVisibleMonth,
                        selectedMonth = uiState.selectedMonth,
                        selectedDayOfMonth = uiState.selectedDayOfMonth,
                        onDaySelected = {
                            viewModel.updateSelectedDayOfMonth(it)
                        })
                }
                AnimatedFadeVisibility(
                    visible = uiState.isMonthYearViewVisible
                ) {
                    MonthAndYearView(
                        selectedMonth = uiState.currentVisibleMonth.number,
                        onMonthChange = { viewModel.updateCurrentVisibleMonth(it) },
                        months = uiState.availableMonths.stream().map { it.name }.toList(),
                        selectedYear = uiState.selectedYearIndex,
                        onYearChange = { viewModel.updateSelectedYearIndex(it) },
                        years = uiState.availableYears.stream().map { it.toString() }.toList()
                    )
                }

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
    numberOfRowsDisplayed: Int = 5
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SwipeLazyColumn(
            selectedIndex = selectedMonth,
            onSelectedIndexChange = onMonthChange,
            items = months,
            numberOfRowsDisplayed = numberOfRowsDisplayed
        )
        SwipeLazyColumn(
            selectedIndex = selectedYear,
            onSelectedIndexChange = onYearChange,
            items = years,
            numberOfRowsDisplayed = numberOfRowsDisplayed
        )
    }
}

@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    height: Dp = 210.dp,
    width: Dp = 120.dp,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    numberOfRowsDisplayed: Int
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = 0) {
        listState.scrollToItem(selectedIndex)
    }


    if (listState.isScrollInProgress) {
        LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
            onSelectedIndexChange(listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > height.value / numberOfRowsDisplayed) 1 else 0)
            Log.d(
                "--TAG",
                "SwipeLazyColumn: ${listState.firstVisibleItemIndex}  ${listState.firstVisibleItemScrollOffset}"
            )
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LazyColumn(
        modifier = modifier
            .height(height)
            .width(width),
        state = listState
    ) {
        items(items.size + numberOfRowsDisplayed - 1) {
            val index = numberOfRowsDisplayed / 2
            val isSelected = it == selectedIndex + index
            val scale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1f)
            Box(
                modifier = Modifier
                    .height(height / numberOfRowsDisplayed)
                    .width(width)
            ) {
                if (it >= index && it < items.size + index) {
                    Text(
                        text = items[it - index],
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(if (isSelected) 1f else 0.5f)
                            .scale(scale),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = if (isSelected) 18.sp else 14.sp,
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
    headerStyle: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray
    ),
    unselectedDateStyle: TextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    selectedDateStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    ),
    selectedDateBackgroundShape: Shape = CircleShape,
    selectedDateBackgroundSize: Dp = 35.dp,
    selectedDateBackgroundColor: Color = Color.Blue,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(7), userScrollEnabled = false, modifier = modifier) {
        items(Constant.days) {
            Text(
                text = it.abbreviation,
                textAlign = TextAlign.Center,
                style = headerStyle
            )
        }
        items(currentVisibleMonth.numberOfDays + currentVisibleMonth.firstDayOfMonth.number - 1) {
            if (it < currentVisibleMonth.firstDayOfMonth.number - 1) return@items
            Box(
                contentAlignment = Alignment.Center
            ) {
                val day = it - currentVisibleMonth.firstDayOfMonth.number + 2
                val isSelected = day == selectedDayOfMonth && selectedMonth == currentVisibleMonth
                val scale by animateFloatAsState(targetValue = if (isSelected) 1.05f else 1f)
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(selectedDateBackgroundSize)
                        .clip(selectedDateBackgroundShape)
                        .clickable { onDaySelected(it - currentVisibleMonth.firstDayOfMonth.number + 2) }
                        .background(if (isSelected) selectedDateBackgroundColor else Color.Transparent)
                ) {
                    Text(
                        text = "$day",
                        textAlign = TextAlign.Center,
                        style = if (isSelected) selectedDateStyle else unselectedDateStyle,
                        modifier = Modifier.scale(scale)
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderMonth(
    modifier: Modifier = Modifier,
    title: String,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onMonthYearClick: () -> Unit,
    isPreviousNextVisible: Boolean,
    arrowColor: Color = Color.Black,
    headerStyle: TextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Text(
            text = title,
            style = headerStyle,
            modifier = modifier
                .padding(start = extraLarge)
                .noRippleClickable { onMonthYearClick() }
                .align(Alignment.CenterStart),
        )

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            AnimatedFadeVisibility(visible = isPreviousNextVisible) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.leftArrow),
                    tint = arrowColor,
                    modifier = Modifier
                        .size(35.dp)
                        .noRippleClickable { onPreviousClick() }
                )
            }
            Spacer(modifier = Modifier.width(medium))
            AnimatedFadeVisibility(visible = isPreviousNextVisible) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.leftArrow),
                    tint = arrowColor,
                    modifier = Modifier
                        .size(35.dp)
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