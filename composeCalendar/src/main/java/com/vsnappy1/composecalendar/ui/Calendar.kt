package com.vsnappy1.composecalendar.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsnappy1.composecalendar.R
import com.vsnappy1.composecalendar.data.Constant
import com.vsnappy1.composecalendar.enums.Months
import com.vsnappy1.composecalendar.theme.small
import kotlin.streams.toList

private const val TAG = "Calendar"

@Composable
fun Calendar(modifier: Modifier = Modifier) {
    val month = Months.FEBRUARY
    Box(modifier = modifier) {
        Column {
            HeaderMonth()
            Spacer(modifier = Modifier.height(small))
            Box {
                DateView(month)
                MonthAndYearView()
            }
        }
    }
}

@Composable
private fun MonthAndYearView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var selectedMonth by remember { mutableStateOf(0) }
        SwipeLazyColumn(
            selectedIndex = selectedMonth,
            onSelectedIndexChange = { selectedMonth = it },
            items = Constant.months.stream().map { it.value }.toList()
        )
        var selectedYear by remember { mutableStateOf(0) }
        SwipeLazyColumn(
            selectedIndex = selectedYear,
            onSelectedIndexChange = { selectedYear = it },
            items = Constant.years.stream().map { it.toString() }.toList()
        )
    }
}

@Composable
private fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,
    width: Dp = 120.dp,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>
) {
    val listState = rememberLazyListState()
    if (listState.isScrollInProgress) {
        LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
            onSelectedIndexChange(listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > height.value / 3) 1 else 0)
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LazyColumn(
        modifier = Modifier
            .height(height)
            .width(width),
        state = listState
    ) {
        items(items.size + 2) {
            val isSelected = it == selectedIndex + 1
            val scale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1f)
            Box(
                modifier = Modifier
                    .height(height / 3)
                    .width(width)
            ) {
                if (it != 0 && it != items.size + 1) {
                    Text(
                        text = items[it - 1],
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
private fun DateView(month: Months) {
    LazyVerticalGrid(columns = GridCells.Fixed(7), userScrollEnabled = false) {
        items(Constant.days) {
            Text(text = it.abbreviation, textAlign = TextAlign.Center)
        }
        items(month.numberOfDays + month.firstDayOfMonth.number) {
            if (it < month.firstDayOfMonth.number) return@items
            Text(text = "${it + 1 - month.firstDayOfMonth.number}", textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun HeaderMonth() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            contentDescription = stringResource(
                id = R.string.leftArrow
            ),
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Row(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "April 2023")
        }

        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = stringResource(
                id = R.string.leftArrow
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@Preview
@Composable
fun DefaultCalendar() {
    Calendar()
}