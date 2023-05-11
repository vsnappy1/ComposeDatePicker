package com.vsnappy1.timepicker.ui.model

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TimePickerConfiguration(
    val backgroundColor: Color = Color.Transparent,
    val backgroundShape: Shape = RoundedCornerShape(0.dp),
    val height: Dp = 210.dp,
    val width: Dp = 120.dp,
    val unselectedTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black.copy(alpha = 0.5f)
    ),
    val selectedTextStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black.copy(alpha = 1f)
    ),
    val scaleFactor: Float = 1.2f,
    val numberOfRowsDisplayed: Int = 7
)