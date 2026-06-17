package com.example.mb_programming_work.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mb_programming_work.ui.theme.LocalColors
import com.example.mb_programming_work.ui.theme.LocalTypography

@Composable
fun MyAppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    numbersOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = LocalColors.current
    val typography = LocalTypography.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = if (isFocused) {
        colors.accent
    } else {
        colors.border
    }

    BasicTextField(
        value = value,
        onValueChange = { input ->
            val filtered = if (numbersOnly) {
                val digitsAndDot = input.filter { it.isDigit() || it == '.' }
                val firstDotIndex = digitsAndDot.indexOfFirst { it == '.' }
                if (firstDotIndex >= 0) {
                    digitsAndDot.take(firstDotIndex + 1) + digitsAndDot.drop(firstDotIndex + 1)
                        .replace(".", "")
                } else {
                    digitsAndDot
                }
            } else input
            onValueChange(filtered)
        },
        singleLine = singleLine,
        readOnly = readOnly,
        textStyle = typography.bodyLarge.copy(
            color = colors.textPrimary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.onBackground),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(colors.background, RoundedCornerShape(16.dp))
            .border(
                width = if (isFocused) {
                    2.dp
                } else {
                    1.dp
                },
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = typography.bodyLarge,
                            color = colors.textSecondary
                        )
                    }
                    innerTextField()
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    trailingIcon()
                }
            }
        }
    )
}
