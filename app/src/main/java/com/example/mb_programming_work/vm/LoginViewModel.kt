package com.example.mb_programming_work.vm

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.mb_programming_work.ui.screens.login.LogInState
import com.example.mb_programming_work.ui.screens.login.LoginEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

        auth.signInWithEmailAndPassword(currentEmail, currentPassword)
            .addOnSuccessListener {
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            }
            .addOnFailureListener { exception ->
                _state.update { it.copy(isLoading = false, isSuccess = false) }
                val errorMessage = exception.localizedMessage ?:"error"
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
    }
}