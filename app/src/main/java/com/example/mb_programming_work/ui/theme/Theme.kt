package com.example.mb_programming_work.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MyAppTheme(
    content: @Composable () -> Unit,
) {
    //material3-is standartul perebs chveni perebit vcvlit
    val materialColorScheme = lightColorScheme(
        background = LightAppColors.background,
        onBackground = LightAppColors.onBackground,
        surface = LightAppColors.surface,
        primary = LightAppColors.primary,
        onPrimary = LightAppColors.onPrimary,
        outline = LightAppColors.outline
    )

    //local cvladebis inicializacia  rata temis monacemebi xelmisawvdomi gaxdes globalurad
    CompositionLocalProvider(
        LocalMyAppColors provides LightAppColors,
        LocalAppTypography provides MyAppTypography
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}

// damxmare obieqti rom martivad davwerot,mag: MyTheme.colors.primary
object MyTheme {
    val colors: MyAppColors
        @Composable
        get() = LocalMyAppColors.current

    val typography: MyTypography
        @Composable
        get() = LocalAppTypography.current
}