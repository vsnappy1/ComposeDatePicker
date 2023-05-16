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
    val unselectedTextStyle: TextStyle,
    val selectedTextStyle: TextStyle,
    val scaleFactor: Float,
    val numberOfRowsDisplayed: Int,
    val selectedAreaHeight: Dp,
    val selectedAreaColor: Color,
    val selectedAreaShape: Shape
) {
    class Builder {
        private var height: Dp = 200.dp
        private var unselectedTextStyle: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black.copy(alpha = 0.5f)
        )
        private var selectedTextStyle: TextStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.W600,
            color = Color.Black
        )
        private var scaleFactor: Float = 1.2f
        private var numberOfRowsDisplayed: Int = 7
        private var selectedAreaHeight: Dp = 35.dp
        private var selectedAreaColor: Color = grayLight.copy(alpha = 0.2f)
        private var selectedAreaShape: Shape = RoundedCornerShape(Size.medium)

        fun height(height: Dp) =
            apply { this.height = height }

        fun unselectedTextStyle(unselectedTextStyle: TextStyle) =
            apply { this.unselectedTextStyle = unselectedTextStyle }

        fun selectedTextStyle(selectedTextStyle: TextStyle) =
            apply { this.selectedTextStyle = selectedTextStyle }

        fun scaleFactor(scaleFactor: Float) =
            apply { this.scaleFactor = scaleFactor }

        fun numberOfRowsDisplayed(numberOfRowsDisplayed: Int) =
            apply { this.numberOfRowsDisplayed = numberOfRowsDisplayed }

        fun selectedAreaHeight(selectedAreaHeight: Dp) =
            apply { this.selectedAreaHeight = selectedAreaHeight }

        fun selectedAreaColor(selectedAreaColor: Color) =
            apply { this.selectedAreaColor = selectedAreaColor }

        fun selectedAreaShape(selectedAreaShape: Shape) =
            apply { this.selectedAreaShape = selectedAreaShape }

        fun build(): TimePickerConfiguration {
            return TimePickerConfiguration(
                height = height,
                unselectedTextStyle = unselectedTextStyle,
                selectedTextStyle = selectedTextStyle,
                scaleFactor = scaleFactor,
                numberOfRowsDisplayed = numberOfRowsDisplayed,
                selectedAreaHeight = selectedAreaHeight,
                selectedAreaColor = selectedAreaColor,
                selectedAreaShape = selectedAreaShape,
            )
        }
    }
}