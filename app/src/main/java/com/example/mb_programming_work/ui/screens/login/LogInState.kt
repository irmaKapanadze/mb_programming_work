package com.example.mb_programming_work.ui.screens.login

data class LogInState(
    val email: String,
    val password: String,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)