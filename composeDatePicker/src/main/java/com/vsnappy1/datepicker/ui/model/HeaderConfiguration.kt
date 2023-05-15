package com.vsnappy1.datepicker.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsnappy1.theme.black

data class HeaderConfiguration(
    val height: Dp = 35.dp,
    val textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = black
    ),
    val arrowSize: Dp = 35.dp,
    val arrowColor: Color = black
)