package com.vsnappy1.composecalendar.ui.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DateViewConfiguration(
    val backgroundColor: Color = Color.Transparent,
    val backgroundShape: Shape = RoundedCornerShape(0.dp),
    val headerTextStyle: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray
    ),
    val unselectedDateStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    val selectedDateStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    val sundayTextColor: Color = Color.Red,
    val selectedDateBackgroundShape: Shape = CircleShape,
    val selectedDateBackgroundSize: Dp = 40.dp,
    val selectedDateBackgroundColor: Color = Color.Blue,
)