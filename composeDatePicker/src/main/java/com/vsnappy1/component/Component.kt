package com.vsnappy1.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.DragInteraction
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
        enter = fadeIn(animationSpec = tween(durationMillis = 400, delayMillis = 200)),
        exit = fadeOut(animationSpec = tween(durationMillis = 250, delayMillis = 100))
    ) {
        content()
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun SwipeLazyColumn(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    height: Dp,
    isAutoScrolling: Boolean,
    isScrollingToSelectedItemEnabled: Boolean = false,
    numberOfRowsDisplayed: Int,
    listState: LazyListState,
    onScrollingStopped: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    var isManualScrolling by remember { mutableStateOf(true) }
    var isInitialWaitOver by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(250)
        isInitialWaitOver =
            true // because not two animateScrollToItem should be called at once and this delay protect that from happening.
    }

    if (isScrollingToSelectedItemEnabled) {
        LaunchedEffect(key1 = selectedIndex) {
            if (isInitialWaitOver) {
                isManualScrolling = false
                if (!listState.isScrollInProgress) {
                    listState.animateScrollToItem(selectedIndex)
                }
                delay(10)
                isManualScrolling = true
            }
        }
    }

    // Update selected item index
    LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
        if (!isAutoScrolling && isManualScrolling && isInitialWaitOver) {
            val index =
                listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > height.value / numberOfRowsDisplayed) 1 else 0
            onSelectedIndexChange(index)
        }
    }

    // For smooth scrolling to center item
    var isAnimateScrollToItemTriggered by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (!isAnimateScrollToItemTriggered) {
            listState.animateScrollToItem(selectedIndex)
            isAnimateScrollToItemTriggered = true
            onScrollingStopped()
        }
    }
    LaunchedEffect(key1 = listState.interactionSource.interactions) {
        listState.interactionSource.interactions.collect {
            if (it is DragInteraction.Start) {
                isAnimateScrollToItemTriggered = false
            }
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