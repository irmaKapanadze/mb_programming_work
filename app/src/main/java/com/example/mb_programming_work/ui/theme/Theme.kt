package com.example.mb_programming_work.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides MyAppTypography
    ) {
        content()
    }
}

object MyTheme {
    val colors: MyAppColors
        @Composable
        get() = LocalColors.current

    val typography: MyTypography
        @Composable
        get() = LocalTypography.current
}