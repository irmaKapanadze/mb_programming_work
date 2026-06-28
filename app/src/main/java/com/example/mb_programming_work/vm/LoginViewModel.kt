package com.example.mb_programming_work.vm

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.data.LoginRepository
import com.example.mb_programming_work.ui.screens.login.LogInState
import com.example.mb_programming_work.ui.screens.login.LoginEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    private val _state = MutableStateFlow(LogInState(email = "", password = ""))
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update { it.copy(email = event.email) }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }

            is LoginEvent.OnLoginClick -> {
                validateAndLogin(event.context)
            }
        }
    }

    private fun validateAndLogin(context: Context) {
        val currentEmail = _state.value.email
        val currentPassword = _state.value.password

        if (currentEmail.isBlank() || currentPassword.isBlank()) {
            Toast.makeText(context, "please,fill all of the fields!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            Toast.makeText(context, "wrong email", Toast.LENGTH_SHORT).show()
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.loginUser(currentEmail, currentPassword)

            result.fold(
                onSuccess = {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { exception ->
                    _state.update { it.copy(isLoading = false, isSuccess = false) }
                    val errorMessage = exception.localizedMessage ?: "error"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}