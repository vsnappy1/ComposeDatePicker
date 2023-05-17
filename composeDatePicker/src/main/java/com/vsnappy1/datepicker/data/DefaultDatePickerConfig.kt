package com.vsnappy1.datepicker.data

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsnappy1.theme.Size
import com.vsnappy1.theme.black
import com.vsnappy1.theme.blue
import com.vsnappy1.theme.grayDark
import com.vsnappy1.theme.grayLight
import com.vsnappy1.theme.red
import com.vsnappy1.theme.white

class DefaultDatePickerConfig private constructor() {

    companion object {
        val height: Dp = 260.dp

        // Header configuration
        val headerHeight: Dp = 35.dp
        val headerTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = black
        )
        val headerArrowSize: Dp = 35.dp
        val headerArrowColor: Color = black

        // Date view configuration
        val daysNameTextStyle: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            color = grayDark
        )
        val dateTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = black
        )
        val selectedDateTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = white
        )
        val sundayTextColor: Color = red
        val disabledDateColor: Color = grayLight
        val selectedDateBackgroundSize: Dp = 35.dp
        val selectedDateBackgroundColor: Color = blue
        val selectedDateBackgroundShape: Shape = CircleShape

        // Month Year view configuration
        val monthYearTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = black.copy(alpha = 0.5f)
        )
        val selectedMonthYearTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            color = black.copy(alpha = 1f)
        )
        const val numberOfMonthYearRowsDisplayed: Int = 7
        const val selectedMonthYearScaleFactor: Float = 1.2f
        val selectedMonthYearAreaHeight: Dp = 35.dp
        val selectedMonthYearAreaColor: Color = grayLight.copy(alpha = 0.2f)
        val selectedMonthYearAreaShape: Shape = RoundedCornerShape(Size.medium)
    }
}