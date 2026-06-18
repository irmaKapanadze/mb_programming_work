package com.example.mb_programming_work.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mb_programming_work.R
import com.example.mb_programming_work.ui.theme.MyTheme
import com.example.mb_programming_work.ui.theme.components.MyAppButton
import com.example.mb_programming_work.ui.theme.components.MyAppPasswordField
import com.example.mb_programming_work.vm.ProfileViewModel

@Composable
fun ProfileScreen(
    onNavigateToFavorites: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLogoutClick()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MyTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(64.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_svgrepo_com),
                    contentDescription = null,
                    tint = MyTheme.colors.onBackground,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = stringResource(R.string.logged_in_as),
                        style = MyTheme.typography.bodySmallest,
                        color = MyTheme.colors.onPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = state.email,
                        color = MyTheme.colors.onBackground,
                        style = MyTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            SettingsSection {
                SettingsItem(
                    title = stringResource(R.string.favorites),
                    textColor = MyTheme.colors.onBackground,
                    leadingIcon = painterResource(R.drawable.heart_shape_svgrepo_com),
                    showDivider = false,
                    showArrow = true,
                    onClick = { onNavigateToFavorites() }
                )
            }

            Spacer(Modifier.height(24.dp))

            SettingsSection {
                SettingsItem(
                    title = stringResource(R.string.log_out),
                    textColor = MyTheme.colors.destructiveColor,
                    leadingIcon = painterResource(id = R.drawable.opened_door_aperture_svgrepo_com),
                    showDivider = true,
                    showArrow = false,
                    onClick = { viewModel.onEvent(ProfileEvent.OnLogOutClick) }
                )
                SettingsItem(
                    title = stringResource(R.string.delete_account),
                    textColor = MyTheme.colors.destructiveColor,
                    leadingIcon = painterResource(id = R.drawable.delete_2_svgrepo_com),
                    showDivider = false,
                    showArrow = false,
                    onClick = { viewModel.onEvent(ProfileEvent.OnDeleteAccountClick) }
                )
            }
        }

        DeleteAccountBottomSheet(
            isOpen = state.isBottomSheetOpen,
            password = state.password,
            isPasswordVisible = state.isPasswordVisible,
            isLoading = state.isLoading,
            passwordChange = { viewModel.onEvent(ProfileEvent.OnPasswordChange(it)) },
            passwordVisibilityChange = { viewModel.onEvent(ProfileEvent.OnPasswordVisibilityChange(it)) },
            onDeleteClick = { viewModel.onEvent(ProfileEvent.OnConfirmDeleteAccount) },
            onDismiss = { viewModel.onEvent(ProfileEvent.OnDismissBottomSheet) }
        )
    }
}

@Composable
fun SettingsSection(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MyTheme.colors.border.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MyTheme.colors.border.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        content()
    }
}

@Composable
fun SettingsItem(
    title: String,
    textColor: Color,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    leadingIcon: Painter? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = title,
                color = textColor,
                style = MyTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            trailingContent?.invoke()

            if (trailingContent == null && showArrow) {
                Icon(
                    painter = painterResource(R.drawable.arrow_turn_up_right_svgrepo_com),
                    contentDescription = null,
                    tint = MyTheme.colors.border,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                thickness = 0.5.dp,
                color = MyTheme.colors.border.copy(alpha = 0.2f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountBottomSheet(
    isOpen: Boolean,
    password: String,
    isPasswordVisible: Boolean,
    isLoading: Boolean,
    passwordChange: (String) -> Unit,
    passwordVisibilityChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!isOpen) return

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MyTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Confirm password to delete account",
                style = MyTheme.typography.bodyLarge,
                color = MyTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            MyAppPasswordField(
                value = password,
                isPasswordVisible = isPasswordVisible,
                onPasswordChange = passwordChange,
                onIconClick = { passwordVisibilityChange(!isPasswordVisible) },
                placeholder = "Password",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = MyTheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                MyAppButton(
                    text = "DELETE ACCOUNT",
                    onClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProfileScreenPreview() {
    MyTheme {
        ProfileScreen({}, {})
    }
}