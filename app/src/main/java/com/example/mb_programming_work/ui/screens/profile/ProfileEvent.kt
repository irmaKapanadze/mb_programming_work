package com.example.mb_programming_work.ui.screens.profile

import android.content.Context

sealed interface ProfileEvent {
    data class OnDarkThemeChanged(val isEnabled: Boolean, val context: Context) : ProfileEvent
    data class LoadSavedTheme(val context: Context) : ProfileEvent
    data object OnDeleteAccountClick : ProfileEvent
    data object OnDismissBottomSheet : ProfileEvent
    data class OnPasswordChange(val password: String) : ProfileEvent
    data class OnPasswordVisibilityChange(val isVisible: Boolean) : ProfileEvent
    data object OnConfirmDeleteAccount : ProfileEvent
    data object OnLogOutClick : ProfileEvent
}