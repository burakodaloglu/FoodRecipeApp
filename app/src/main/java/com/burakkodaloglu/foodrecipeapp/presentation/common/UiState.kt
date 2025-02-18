package com.burakkodaloglu.foodrecipeapp.presentation.common

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)