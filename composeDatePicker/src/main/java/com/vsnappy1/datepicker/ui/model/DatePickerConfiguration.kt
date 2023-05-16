package com.vsnappy1.datepicker.ui.model

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

class DatePickerConfiguration private constructor(
    val height: Dp,
    val headerHeight: Dp,
    val headerTextStyle: TextStyle,
    val headerArrowSize: Dp,
    val headerArrowColor: Color,
    val daysNameTextStyle: TextStyle,
    val dateTextStyle: TextStyle,
    val selectedDateTextStyle: TextStyle,
    val sundayTextColor: Color,
    val disabledDateColor: Color,
    val selectedDateBackgroundSize: Dp,
    val selectedDateBackgroundColor: Color,
    val selectedDateBackgroundShape: Shape,
    val monthYearTextStyle: TextStyle,
    val selectedMonthYearTextStyle: TextStyle,
    val selectedMonthYearScaleFactor: Float,
    val numberOfMonthYearRowsDisplayed: Int,
    val selectedMonthYearAreaHeight: Dp,
    val selectedMonthYearAreaColor: Color,
    val selectedMonthYearAreaShape: Shape
) {


    class Builder {
        private var height: Dp = 260.dp

        // Header configuration
        private var headerHeight: Dp = 35.dp
        private var headerTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = black
        )
        private var headerArrowSize: Dp = 35.dp
        private var headerArrowColor: Color = black

        // Date view configuration
        private var daysNameTextStyle: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            color = grayDark
        )
        private var dateTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = black
        )
        private var selectedDateTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            color = white
        )
        private var sundayTextColor: Color = red
        private var disabledDateColor: Color = grayLight
        private var selectedDateBackgroundSize: Dp = 35.dp
        private var selectedDateBackgroundColor: Color = blue
        private var selectedDateBackgroundShape: Shape = CircleShape

        // Month Year view configuration
        private var monthYearTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = black.copy(alpha = 0.5f)
        )
        private var selectedMonthYearTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            color = black.copy(alpha = 1f)
        )
        private var numberOfMonthYearRowsDisplayed: Int = 7
        private var selectedMonthYearScaleFactor: Float = 1.2f
        private var selectedMonthYearAreaHeight: Dp = 35.dp
        private var selectedMonthYearAreaColor: Color = grayLight.copy(alpha = 0.2f)
        private var selectedMonthYearAreaShape: Shape = RoundedCornerShape(Size.medium)

        fun height(height: Dp) =
            apply { this.height = height }

        fun headerHeight(height: Dp) =
            apply { this.headerHeight = height }

        fun headerTextStyle(textStyle: TextStyle) =
            apply { this.headerTextStyle = textStyle }

        fun headerArrowSize(size: Dp) =
            apply { this.headerArrowSize = size }

        fun headerArrowColor(color: Color) =
            apply { this.headerArrowColor = color }

        fun daysNameTextStyle(textStyle: TextStyle) =
            apply { this.daysNameTextStyle = textStyle }

        fun dateTextStyle(textStyle: TextStyle) =
            apply { this.dateTextStyle = textStyle }

        fun selectedDateTextStyle(textStyle: TextStyle) =
            apply { this.selectedDateTextStyle = textStyle }

        fun sundayTextColor(color: Color) =
            apply { this.sundayTextColor = color }

        fun disabledDateColor(color: Color) =
            apply { this.disabledDateColor = color }

        fun selectedDateBackgroundSize(size: Dp) =
            apply { this.selectedDateBackgroundSize = size }

        fun selectedDateBackgroundColor(color: Color) =
            apply { this.selectedDateBackgroundColor = color }

        fun selectedDateBackgroundShape(shape: Shape) =
            apply { this.selectedDateBackgroundShape = shape }

        fun monthYearTextStyle(textStyle: TextStyle) =
            apply { this.monthYearTextStyle = textStyle }

        fun selectedMonthYearTextStyle(textStyle: TextStyle) =
            apply { this.selectedMonthYearTextStyle = textStyle }

        fun selectedMonthYearScaleFactor(scaleFactor: Float) =
            apply { this.selectedMonthYearScaleFactor = scaleFactor }

        fun numberOfMonthYearRowsDisplayed(count: Int) =
            apply { this.numberOfMonthYearRowsDisplayed = count }

        fun selectedMonthYearAreaHeight(height: Dp) =
            apply { this.selectedMonthYearAreaHeight = height }

        fun selectedMonthYearAreaColor(color: Color) =
            apply { this.selectedMonthYearAreaColor = color }

        fun selectedMonthYearAreaShape(shape: Shape) =
            apply { this.selectedMonthYearAreaShape = shape }

        fun build(): DatePickerConfiguration {
            return DatePickerConfiguration(
                height = height,
                headerHeight = headerHeight,
                headerTextStyle = headerTextStyle,
                headerArrowSize = headerArrowSize,
                headerArrowColor = headerArrowColor,
                daysNameTextStyle = daysNameTextStyle,
                dateTextStyle = dateTextStyle,
                selectedDateTextStyle = selectedDateTextStyle,
                sundayTextColor = sundayTextColor,
                disabledDateColor = disabledDateColor,
                selectedDateBackgroundSize = selectedDateBackgroundSize,
                selectedDateBackgroundColor = selectedDateBackgroundColor,
                selectedDateBackgroundShape = selectedDateBackgroundShape,
                monthYearTextStyle = monthYearTextStyle,
                selectedMonthYearTextStyle = selectedMonthYearTextStyle,
                selectedMonthYearScaleFactor = selectedMonthYearScaleFactor,
                numberOfMonthYearRowsDisplayed = numberOfMonthYearRowsDisplayed,
                selectedMonthYearAreaHeight = selectedMonthYearAreaHeight,
                selectedMonthYearAreaColor = selectedMonthYearAreaColor,
                selectedMonthYearAreaShape = selectedMonthYearAreaShape
            )
        }
    }
}