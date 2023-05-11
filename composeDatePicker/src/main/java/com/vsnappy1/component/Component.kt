package com.vsnappy1.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay

@Composable
fun AnimatedFadeVisibility(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 400, delayMillis = 100)),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
    ) {
        content()
    }
}

//TODO improve scrolling logic so scrolling looks more natural while keeping items clickable.
@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    height: Dp,
    isAutoScrolling: Boolean,
    numberOfRowsDisplayed: Int,
    listState: LazyListState,
    content: LazyListScope.() -> Unit
) {
    var lastSelectedIndex by remember { mutableStateOf(0) }
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        listState.scrollToItem(selectedIndex)
    }
    if (!isAutoScrolling) {
        LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
            val index =
                listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > height.value / numberOfRowsDisplayed) 1 else 0
            onSelectedIndexChange(index)
            count = if (index == lastSelectedIndex) ++count else 0
            if (count >= 4) {
                listState.animateScrollToItem(selectedIndex)
            }
            lastSelectedIndex = index
        }
    }

    LazyColumn(
        modifier = modifier
            .height(height),
        state = listState
    ) {
        content()
    }
}