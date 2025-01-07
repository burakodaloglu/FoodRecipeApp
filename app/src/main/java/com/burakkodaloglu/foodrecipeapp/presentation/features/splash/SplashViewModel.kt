package com.burakkodaloglu.foodrecipeapp.presentation.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.foodrecipeapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiEffect = Channel<SplashUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        checkUserLoggedInStatus()
    }

    private fun checkUserLoggedInStatus() = viewModelScope.launch {
        if (authRepository.isUserLoggedIn()) {
            emitUiEffect(SplashUiEffect.NavigateToHome)
        } else {
            emitUiEffect(SplashUiEffect.NavigateToLogin)
        }
    }

    private suspend fun emitUiEffect(effect: SplashUiEffect) {
        _uiEffect.send(effect)
    }
}

sealed class SplashUiEffect {
    object NavigateToHome : SplashUiEffect()
    object NavigateToLogin : SplashUiEffect()
}
