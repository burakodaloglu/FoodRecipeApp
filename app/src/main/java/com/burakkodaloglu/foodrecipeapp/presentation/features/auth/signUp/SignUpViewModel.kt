package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.foodrecipeapp.data.repository.AuthRepository
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpContract.UiAction
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpContract.UiEffect
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            //is UiAction.SignUpClick -> signUp()
            is UiAction.ChangeEmail -> updateUiState { copy(email = uiAction.email) }
            is UiAction.ChangeLastName -> updateUiState { copy(lastName = uiAction.lastName) }
            is UiAction.ChangePassword -> updateUiState { copy(password = uiAction.password) }
            is UiAction.ChangeName -> updateUiState { copy(name = uiAction.name) }
        }
    }
/*
    private fun signUp() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signUp(uiState.value.email, uiState.value.password)) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false) }
                emitUiEffect(UiEffect.ShowToast("Your account has been created successfully."))
                emitUiEffect(UiEffect.GoToMainScreen)
            }

            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
                emitUiEffect(UiEffect.ShowToast(result.exception.message.orEmpty()))
            }
        }
    }

 */

    fun signUpWithName(
        email: String,
        password: String,
        name: String,
        lastName:String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val signUpResult = authRepository.signUp(email, password)) {
                is Resource.Success -> {
                    updateUiState { copy(isLoading = false) }
                    val userId = signUpResult.data
                    when (val saveNameResult = authRepository.saveFullNameToFirebase(userId, name, lastName)) {
                        is Resource.Success -> {
                            updateUiState { copy(isLoading = false) }
                            onSuccess()
                        }

                        is Resource.Error -> {
                            updateUiState { copy(isLoading = false) }
                            onError(saveNameResult.exception.message ?: "Failed to save name")
                        }
                    }
                }

                is Resource.Error ->{
                    updateUiState { copy(isLoading = false) }
                    onError(signUpResult.exception.message ?: "Sign-up failed")
                }
            }
        }
    }


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}