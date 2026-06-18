package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import com.example.mb_programming_work.ui.screens.profile.ProfileEvent
import com.example.mb_programming_work.ui.screens.profile.ProfileState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        val currentUser = auth.currentUser
        _state.update { it.copy(email = currentUser?.email ?: "guest@user.com") }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnDarkThemeChanged -> {
                _state.update { it.copy(isDarkThemeEnabled = event.isEnabled) }
            }

            ProfileEvent.OnDeleteAccountClick -> {
                _state.update { it.copy(isBottomSheetOpen = true) }
            }

            ProfileEvent.OnDismissBottomSheet -> {
                _state.update { it.copy(isBottomSheetOpen = false, password = "") }
            }

            is ProfileEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }

            is ProfileEvent.OnPasswordVisibilityChange -> {
                _state.update { it.copy(isPasswordVisible = event.isVisible) }
            }

            ProfileEvent.OnLogOutClick -> {
                auth.signOut()
                _state.update { it.copy(isSuccess = true) }
            }

            ProfileEvent.OnConfirmDeleteAccount -> {
                deleteAccount()
            }
        }
    }

    private fun deleteAccount() {
        val user = auth.currentUser
        val password = _state.value.password

        if (user == null || password.isEmpty() || user.email == null) {
            return
        }

        _state.update { it.copy(isLoading = true) }
        val credential = EmailAuthProvider.getCredential(user.email!!, password)

        user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
            if (reAuthTask.isSuccessful) {
                user.delete().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        auth.signOut()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isBottomSheetOpen = false,
                                isSuccess = true
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            } else {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}