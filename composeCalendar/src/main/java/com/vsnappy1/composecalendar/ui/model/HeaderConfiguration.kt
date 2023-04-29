package com.vsnappy1.composecalendar.ui.model

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HeaderConfiguration(
    val backgroundColor: Color = Color.Transparent,
    val backgroundShape: Shape = RoundedCornerShape(0.dp),
    val arrowColor: Color = Color.Black,
    val arrowSize: Dp = 35.dp,
    val height: Dp = 35.dp,
    val textStyle: TextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
)