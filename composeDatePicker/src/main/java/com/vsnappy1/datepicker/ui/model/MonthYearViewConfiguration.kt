package com.vsnappy1.datepicker.ui.model

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
import com.vsnappy1.theme.grayLight

data class MonthYearViewConfiguration(
    val backgroundColor: Color = Color.Transparent,
    val backgroundShape: Shape = RoundedCornerShape(0.dp),

    val unselectedTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = black.copy(alpha = 0.5f)
    ),
    val selectedTextStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = black.copy(alpha = 1f)
    ),
    val scaleFactor: Float = 1.2f,
    val height: Dp = 210.dp,
    val width: Dp = 115.dp - selectedTextStyle.fontSize.value.dp * scaleFactor,
    val numberOfRowsDisplayed: Int = 7,
    val selectedAreaHeight: Dp = 40.dp,
    val selectedAreaColor: Color = grayLight.copy(alpha = 0.2f),
    val selectedAreaShape: Shape = RoundedCornerShape(Size.medium)
)