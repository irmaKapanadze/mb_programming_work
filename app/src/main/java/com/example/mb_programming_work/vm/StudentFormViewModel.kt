package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentFormViewModel : ViewModel() {
    //public da private statebi

    //saxeli
    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    //tarighi
    private val _dateState = MutableStateFlow("")
    val dateState: StateFlow<String> = _dateState.asStateFlow()

    //email
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState.asStateFlow()

    //monishnuli pavoriti mimartuleba
    private val _selectedOption = MutableStateFlow("")
    val selectedOption: StateFlow<String> = _selectedOption.asStateFlow()

    //etanxmeba tu ara momxmarebeli wesebsa da pirobebs
    private val _isAgreed = MutableStateFlow(false)
    val isAgreed: StateFlow<Boolean> = _isAgreed.asStateFlow()


    //event-ebi state-is cvlilebaze
    fun onNameChange(newValue: String) {
        _nameState.value = newValue
    }

    fun onDateChange(newValue: String) {
        _dateState.value = newValue
    }

    fun onEmailChange(newValue: String) {
        _emailState.value = newValue
    }

    fun onOptionSelect(newValue: String) {
        _selectedOption.value = newValue
    }

    fun onAgreementChange(newValue: Boolean) {
        _isAgreed.value = newValue
    }


    //es punkcia gamoiyeneba field-ebis validaciistvis
    fun validateForm(): Boolean {
        return _nameState.value.isNotBlank() &&
                _dateState.value.isNotBlank() &&
                _emailState.value.isNotBlank() &&
                _selectedOption.value.isNotBlank() &&
                _isAgreed.value
    }
}