package com.vsnappy1.composecalendar.extension

import android.content.Context
import android.util.TypedValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


// TODO refactor code to make it more readable
// TODO add sliding effect when next or previous arrow is pressed
// TODO Develop Time Picker

fun Int.isLeapYear(): Boolean {
    // A year is a leap year if it is divisible by 4
    // but not divisible by 100, unless it is also divisible by 400
    return this % 4 == 0 && (this % 100 != 0 || this % 400 == 0)
}

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    ) {
        onClick()
    }
}

fun Int.toDp(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this.toFloat(),
        context.resources.displayMetrics
    ) / context.resources.displayMetrics.density
}