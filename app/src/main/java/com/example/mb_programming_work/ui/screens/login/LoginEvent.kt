package com.example.mb_programming_work.ui.screens.login

import android.content.Context

sealed interface LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent
    data class OnPasswordChange(val password: String) : LoginEvent
    data class OnLoginClick(val context: Context) : LoginEvent
}