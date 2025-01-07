package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn

object SignInContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
    )

    sealed class UiAction {
        data object SignInClick : UiAction()
        data class ChangeEmail(val email: String) : UiAction()
        data class ChangePassword(val password: String) : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object GoToMainScreen : UiEffect()
    }
}