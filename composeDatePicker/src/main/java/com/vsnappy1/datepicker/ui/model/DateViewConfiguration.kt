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
import com.vsnappy1.theme.black
import com.vsnappy1.theme.blue
import com.vsnappy1.theme.grayDark
import com.vsnappy1.theme.grayLight
import com.vsnappy1.theme.red
import com.vsnappy1.theme.white

data class DateViewConfiguration(
    val headerTextStyle: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = grayDark
    ),
    val unselectedDateStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = black
    ),
    val selectedDateStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = white
    ),
    val sundayTextColor: Color = red,
    val disabledDateColor: Color = grayLight,
    val selectedDateBackgroundSize: Dp = 40.dp,
    val selectedDateBackgroundColor: Color = blue,
    val selectedDateBackgroundShape: Shape = CircleShape,
)