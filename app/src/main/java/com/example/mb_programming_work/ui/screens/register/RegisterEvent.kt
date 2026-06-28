package com.example.mb_programming_work.ui.screens.register

import android.content.Context

sealed interface RegisterEvent {
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnPasswordChange(val password: String) : RegisterEvent
    data class OnPassword2Change(val password2: String) : RegisterEvent
    data class OnRegisterClick(val context: Context) : RegisterEvent
}