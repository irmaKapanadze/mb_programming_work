package com.example.mb_programming_work.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//palitristvis monacemta modeli
data class MyAppColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val primary: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val border: Color,
    val accent: Color,
    val outline: Color,
)

//light theme-is shesabamis palitras
val LightAppColors = MyAppColors(
    background = Color(0xFFF4F7F9),
    onBackground = Color(0xFF0F1417),
    surface = Color(0xFFFFFFFF),
    primary = Color(0xFF1F2A30),
    onPrimary = Color.White,
    textPrimary = Color(0xFF0F1417),
    textSecondary = Color(0xFF5F6B73),
    border = Color(0xFFE3E8EC),
    accent = Color(0xFF00C853),
    outline = Color(0xFFC5CED4)
)

//es sawiroa imisatvis rom kodis nebismieri adgilidan gamoviyenot,magalitad:MyAppTheme.colors.background
val LocalMyAppColors = staticCompositionLocalOf { LightAppColors }