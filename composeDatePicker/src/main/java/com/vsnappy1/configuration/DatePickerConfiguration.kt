package com.vsnappy1.configuration

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
    val dateDaysTextStyle: TextStyle,
    val dateUnselectedTextStyle: TextStyle,
    val dateSelectedTextStyle: TextStyle,
    val dateSundayTextColor: Color,
    val dateDisabledColor: Color,
    val dateSelectedBackgroundSize: Dp,
    val dateSelectedBackgroundColor: Color,
    val dateSelectedBackgroundShape: Shape,
    val monthYearUnselectedTextStyle: TextStyle,
    val monthYearSelectedTextStyle: TextStyle,
    val monthYearSelectedItemScaleFactor: Float,
    val monthYearNumberOfRowsDisplayed: Int,
    val monthYearSelectedAreaHeight: Dp,
    val monthYearSelectedAreaColor: Color,
    val monthYearSelectedAreaShape: Shape
) {


    class Builder {
        private var height: Dp = 288.dp
        private var headerHeight: Dp = 35.dp
        private var headerTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = black
        )
        private var headerArrowSize: Dp = 35.dp
        private var headerArrowColor: Color = black

        private var dateDaysTextStyle: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = grayDark
        )
        private var dateUnselectedTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W400,
            color = black
        )
        private var dateSelectedTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W500,
            color = white
        )
        private var dateSundayTextColor: Color = red
        private var dateDisabledColor: Color = grayLight
        private var dateSelectedBackgroundSize: Dp = 40.dp
        private var dateSelectedBackgroundColor: Color = blue
        private var dateSelectedBackgroundShape: Shape = CircleShape

        private var monthYearUnselectedTextStyle: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = black.copy(alpha = 0.5f)
        )
        private var monthYearSelectedTextStyle: TextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = black.copy(alpha = 1f)
        )
        private var monthYearSelectedItemScaleFactor: Float = 1.2f
        private var monthYearNumberOfRowsDisplayed: Int = 7
        private var monthYearSelectedAreaHeight: Dp = 40.dp
        private var monthYearSelectedAreaColor: Color = grayLight.copy(alpha = 0.2f)
        private var monthYearSelectedAreaShape: Shape = RoundedCornerShape(Size.medium)

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

        fun dateDaysTextStyle(textStyle: TextStyle) =
            apply { this.dateDaysTextStyle = textStyle }

        fun dateUnselectedTextStyle(textStyle: TextStyle) =
            apply { this.dateUnselectedTextStyle = textStyle }

        fun dateSelectedTextStyle(textStyle: TextStyle) =
            apply { this.dateSelectedTextStyle = textStyle }

        fun dateSundayTextColor(color: Color) =
            apply { this.dateSundayTextColor = color }

        fun dateDisabledColor(color: Color) =
            apply { this.dateDisabledColor = color }

        fun dateSelectedBackgroundSize(size: Dp) =
            apply { this.dateSelectedBackgroundSize = size }

        fun dateSelectedBackgroundColor(color: Color) =
            apply { this.dateSelectedBackgroundColor = color }

        fun dateSelectedBackgroundShape(shape: Shape) =
            apply { this.dateSelectedBackgroundShape = shape }

        fun monthYearUnselectedTextStyle(textStyle: TextStyle) =
            apply { this.monthYearUnselectedTextStyle = textStyle }

        fun monthYearSelectedTextStyle(textStyle: TextStyle) =
            apply { this.monthYearSelectedTextStyle = textStyle }

        fun monthYearSelectedItemScaleFactor(scaleFactor: Float) =
            apply { this.monthYearSelectedItemScaleFactor = scaleFactor }

        fun monthYearNumberOfRowsDisplayed(count: Int) =
            apply { this.monthYearNumberOfRowsDisplayed = count }

        fun monthYearSelectedAreaHeight(height: Dp) =
            apply { this.monthYearSelectedAreaHeight = height }

        fun monthYearSelectedAreaColor(color: Color) =
            apply { this.monthYearSelectedAreaColor = color }

        fun monthYearSelectedAreaShape(shape: Shape) =
            apply { this.monthYearSelectedAreaShape = shape }

        fun build(): DatePickerConfiguration {
            return DatePickerConfiguration(
                height,
                headerHeight,
                headerTextStyle,
                headerArrowSize,
                headerArrowColor,
                dateDaysTextStyle,
                dateUnselectedTextStyle,
                dateSelectedTextStyle,
                dateSundayTextColor,
                dateDisabledColor,
                dateSelectedBackgroundSize,
                dateSelectedBackgroundColor,
                dateSelectedBackgroundShape,
                monthYearUnselectedTextStyle,
                monthYearSelectedTextStyle,
                monthYearSelectedItemScaleFactor,
                monthYearNumberOfRowsDisplayed,
                monthYearSelectedAreaHeight,
                monthYearSelectedAreaColor,
                monthYearSelectedAreaShape
            )
        }
    }
}