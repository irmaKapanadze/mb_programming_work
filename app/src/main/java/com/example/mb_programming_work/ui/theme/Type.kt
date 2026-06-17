package com.example.mb_programming_work.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mb_programming_work.R


val main_font = FontFamily(
    Font(R.font.comfortaa_light, FontWeight.Light),
    Font(R.font.comfortaa_regular, FontWeight.Normal),
    Font(R.font.comfortaa_bold, FontWeight.Bold)
)


data class MyTypography(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val labelLarge: TextStyle,
    val bodyLargest: TextStyle,
    val bodySmallest: TextStyle,
)


val MyAppTypography = MyTypography(
    headlineLarge = TextStyle(
        fontFamily = main_font,
        fontSize = 34.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = main_font,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = main_font,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = main_font,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),

    labelLarge = TextStyle(
        fontFamily = main_font,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.6.sp
    ),

    bodyLargest = TextStyle(
        fontFamily = main_font,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    ),
    bodySmallest = TextStyle(
        fontFamily = main_font,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    )
)

val LocalTypography = staticCompositionLocalOf<MyTypography> {
    error("MyTypography not provided")
}