package com.burakkodaloglu.foodrecipeapp.presentation.features.home.meal

import com.burakkodaloglu.foodrecipeapp.domain.entities.Meal

data class MealListState(
    val mealList: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
