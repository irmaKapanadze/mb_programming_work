package com.example.mb_programming_work.ui.screens.profile

data class ProfileState(
    val email: String = "",
    val isDarkThemeEnabled: Boolean = true,
    val isBottomSheetOpen: Boolean = false,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)