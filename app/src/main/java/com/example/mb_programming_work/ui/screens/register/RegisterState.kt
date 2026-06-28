package com.example.mb_programming_work.ui.screens.register

data class RegisterState(
    val email: String,
    val password: String,
    val password2: String,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)