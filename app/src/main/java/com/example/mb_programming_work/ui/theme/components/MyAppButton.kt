package com.example.mb_programming_work.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mb_programming_work.ui.theme.LocalColors
import com.example.mb_programming_work.ui.theme.LocalTypography

enum class AppButtonType {
    Primary,
    Secondary,
    Outlined,
    Accent
}

@Composable
fun MyAppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: AppButtonType = AppButtonType.Primary,
) {
    val colors = LocalColors.current
    val typography = LocalTypography.current

    val backgroundColor = when (type) {
        AppButtonType.Primary -> colors.primary
        AppButtonType.Secondary,
        AppButtonType.Outlined,
            -> colors.surface

        AppButtonType.Accent -> colors.accent
    }

    val textColor = when (type) {
        AppButtonType.Primary -> colors.onPrimary
        else -> colors.textPrimary
    }

    val border = when (type) {
        AppButtonType.Primary -> null
        else -> BorderStroke(1.dp, colors.border)
    }

    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .then(
                if (border != null)
                    Modifier.border(border, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.labelLarge,
            color = textColor
        )
    }
}
