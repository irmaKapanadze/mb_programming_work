package com.example.mb_programming_work.ui.screens.profile

sealed interface ProfileEvent {
    data class OnDarkThemeChanged(val isEnabled: Boolean) : ProfileEvent
    data object OnDeleteAccountClick : ProfileEvent
    data object OnDismissBottomSheet : ProfileEvent
    data class OnPasswordChange(val password: String) : ProfileEvent
    data class OnPasswordVisibilityChange(val isVisible: Boolean) : ProfileEvent
    data object OnConfirmDeleteAccount : ProfileEvent
    data object OnLogOutClick : ProfileEvent
}