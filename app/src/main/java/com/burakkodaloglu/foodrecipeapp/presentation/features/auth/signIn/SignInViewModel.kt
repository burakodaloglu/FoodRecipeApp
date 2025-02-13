package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.foodrecipeapp.data.repository.AuthRepository
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
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
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInContract.UiState
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInContract.UiAction
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInContract.UiEffect

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.SignInClick -> signIn()
            is UiAction.ChangeEmail -> updateUiState { copy(email = uiAction.email) }
            is UiAction.ChangePassword -> updateUiState { copy(password = uiAction.password) }
        }
    }

    private fun signIn() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signIn(uiState.value.email, uiState.value.password)) {
            is Resource.Loading->updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                updateUiState { copy(isLoading = false) }
                emitUiEffect(UiEffect.GoToMainScreen)
            }
            is Resource.Error -> {
                updateUiState { copy(isLoading = false) }
                emitUiEffect(UiEffect.ShowToast(result.message.orEmpty()))
            }
        }
    }

    fun fetchUserName(userId: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = authRepository.getFullNameFromFirebase(userId)) {
                is Resource.Loading->updateUiState { copy(isLoading = true) }
                is Resource.Success -> result.data?.let { onResult(it) }
                is Resource.Error -> onResult("User")
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