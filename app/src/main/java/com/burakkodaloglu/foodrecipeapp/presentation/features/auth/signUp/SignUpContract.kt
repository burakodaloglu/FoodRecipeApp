package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val name: String = "",
        val email: String = "",
        val password: String = "",
    )

    sealed class UiAction {
        data object SignUpClick : UiAction()
        data class ChangeName(val name: String) : UiAction()
        data class ChangeEmail(val email: String) : UiAction()
        data class ChangePassword(val password: String) : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object GoToSignIn : UiEffect()
    }
}