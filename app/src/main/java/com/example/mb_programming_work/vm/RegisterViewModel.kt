package com.example.mb_programming_work.vm

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.data.RegisterRepository
import com.example.mb_programming_work.ui.screens.register.RegisterEvent
import com.example.mb_programming_work.ui.screens.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = RegisterRepository()

    private val _state = MutableStateFlow(RegisterState(email = "", password = "", password2 = ""))
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailChange -> {
                _state.update { it.copy(email = event.email) }
            }

            is RegisterEvent.OnPassword2Change -> {
                _state.update { it.copy(password2 = event.password2) }
            }

            is RegisterEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }

            is RegisterEvent.OnRegisterClick -> {
                validateAndRegister(event.context)
            }
        }
    }

    private fun validateAndRegister(context: Context) {
        val currentEmail = _state.value.email
        val currentPassword = _state.value.password
        val currentPassword2 = _state.value.password2

        if (currentEmail.isBlank() || currentPassword.isBlank() || currentPassword2.isBlank()) {
            Toast.makeText(context, "please fill all of the fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            Toast.makeText(context, "wrong email", Toast.LENGTH_SHORT).show()
            return
        }

        if (currentPassword != currentPassword2) {
            Toast.makeText(context, "passwords don't match", Toast.LENGTH_SHORT).show()
            return
        }

        if (currentPassword.length < 8) {
            Toast.makeText(
                context,
                "password should be at least 8 symbols long",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.registerUser(currentEmail, currentPassword)

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