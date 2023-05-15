package com.vsnappy1.extension

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import java.util.Calendar

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

fun Calendar.isEqual(calendar: Calendar?): Boolean {
    calendar?.let {
        return get(Calendar.YEAR) == it[Calendar.YEAR] &&
                get(Calendar.MONTH) == it[Calendar.MONTH] &&
                get(Calendar.DAY_OF_MONTH) == it[Calendar.DAY_OF_MONTH]
    }
    return false
}

fun Int.toDp(): Dp = (this / Resources.getSystem().displayMetrics.density).dp

fun TextUnit.spToDp(density: Density): Dp{
    return with(density) {
        this@spToDp.toDp()
    }
}