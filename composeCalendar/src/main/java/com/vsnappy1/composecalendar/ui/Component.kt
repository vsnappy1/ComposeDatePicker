package com.vsnappy1.composecalendar.ui

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
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
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
    isManualScrolling: Boolean,
    numberOfRowsDisplayed: Int,
    listState: LazyListState,
    content: LazyListScope.() -> Unit
) {
    var dragStarted by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        listState.scrollToItem(selectedIndex)
    }
    if(isManualScrolling){
        LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {
            onSelectedIndexChange(listState.firstVisibleItemIndex + if (listState.firstVisibleItemScrollOffset > height.value / numberOfRowsDisplayed) 1 else 0)
        }
        LaunchedEffect(key1 = dragStarted) {
            listState.animateScrollToItem(selectedIndex)
        }

        LaunchedEffect(key1 = Unit) {
            listState.interactionSource.interactions.collect {
                if (it is DragInteraction.Stop) {
                    delay(100)
                    dragStarted = false
                } else if (it is DragInteraction.Start) {
                    dragStarted = true
                }
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