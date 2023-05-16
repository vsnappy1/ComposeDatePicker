package com.vsnappy1.timepicker.ui.model

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsnappy1.theme.Size
import com.vsnappy1.theme.grayLight

class TimePickerConfiguration private constructor(
    val height: Dp,
    val timeTextStyle: TextStyle,
    val selectedTimeTextStyle: TextStyle,
    val numberOfTimeRowsDisplayed: Int,
    val selectedTimeScaleFactor: Float,
    val selectedTimeAreaHeight: Dp,
    val selectedTimeAreaColor: Color,
    val selectedTimeAreaShape: Shape
) {
    class Builder {
        private var height: Dp = 200.dp
        private var timeTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black.copy(alpha = 0.5f)
        )
        private var selectedTimeTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            color = Color.Black
        )
        private var numberOfTimeRowsDisplayed: Int = 7
        private var selectedTimeScaleFactor: Float = 1.2f
        private var selectedTimeAreaHeight: Dp = 35.dp
        private var selectedTimeAreaColor: Color = grayLight.copy(alpha = 0.2f)
        private var selectedTimeAreaShape: Shape = RoundedCornerShape(Size.medium)

        fun height(height: Dp) =
            apply { this.height = height }

        fun timeTextStyle(unselectedTextStyle: TextStyle) =
            apply { this.timeTextStyle = unselectedTextStyle }

        fun selectedTimeTextStyle(selectedTextStyle: TextStyle) =
            apply { this.selectedTimeTextStyle = selectedTextStyle }

        fun selectedTimeScaleFactor(scaleFactor: Float) =
            apply { this.selectedTimeScaleFactor = scaleFactor }

        fun numberOfTimeRowsDisplayed(numberOfRowsDisplayed: Int) =
            apply { this.numberOfTimeRowsDisplayed = numberOfRowsDisplayed }

        fun selectedTimeAreaHeight(selectedAreaHeight: Dp) =
            apply { this.selectedTimeAreaHeight = selectedAreaHeight }

        fun selectedTimeAreaColor(selectedAreaColor: Color) =
            apply { this.selectedTimeAreaColor = selectedAreaColor }

        fun selectedTimeAreaShape(selectedAreaShape: Shape) =
            apply { this.selectedTimeAreaShape = selectedAreaShape }

        fun build(): TimePickerConfiguration {
            return TimePickerConfiguration(
                height = height,
                timeTextStyle = timeTextStyle,
                selectedTimeTextStyle = selectedTimeTextStyle,
                numberOfTimeRowsDisplayed = numberOfTimeRowsDisplayed,
                selectedTimeScaleFactor = selectedTimeScaleFactor,
                selectedTimeAreaHeight = selectedTimeAreaHeight,
                selectedTimeAreaColor = selectedTimeAreaColor,
                selectedTimeAreaShape = selectedTimeAreaShape,
            )
        }
    }
}