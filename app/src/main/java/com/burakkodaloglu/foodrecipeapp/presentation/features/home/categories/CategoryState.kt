package com.burakkodaloglu.foodrecipeapp.presentation.features.home.categories

import com.burakkodaloglu.foodrecipeapp.domain.entities.Category

data class CategoryState(
    val categoryList: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
