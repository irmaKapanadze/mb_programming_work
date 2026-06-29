package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.data.datastore.DataStoreManager
import com.example.mb_programming_work.data.repository.ProfileRepository
import com.example.mb_programming_work.ui.screens.profile.ProfileEvent
import com.example.mb_programming_work.ui.screens.profile.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(email = repository.getCurrentUserEmail()) }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnDarkThemeChanged -> {
                _state.update { it.copy(isDarkThemeEnabled = event.isEnabled) }

                val dataStoreManager = DataStoreManager(event.context)
                viewModelScope.launch {
                    dataStoreManager.saveThemeSetting(event.isEnabled)
                }
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
                repository.logOut()
                _state.update { it.copy(isSuccess = true) }
            }

            ProfileEvent.OnConfirmDeleteAccount -> {
                deleteAccount()
            }

            is ProfileEvent.LoadSavedTheme -> {
                val dataStoreManager = DataStoreManager(event.context)
                viewModelScope.launch {
                    dataStoreManager.isDarkThemeFlow.collect { isDarkTheme ->
                        isDarkTheme?.let { savedTheme ->
                            _state.update { it.copy(isDarkThemeEnabled = savedTheme) }
                        }
                    }
                }
            }
        }
    }

    private fun deleteAccount() {
        val password = _state.value.password
        if (password.isEmpty()) return
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.deleteUserAccount(password)
            result.fold(
                onSuccess = {
                    _state.update {
                        it.copy(isLoading = false, isBottomSheetOpen = false, isSuccess = true)
                    }
                },
                onFailure = {
                    _state.update { it.copy(isLoading = false) }
                }
            )
        }
    }
}