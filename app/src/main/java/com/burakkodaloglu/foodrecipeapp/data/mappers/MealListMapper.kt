package com.burakkodaloglu.foodrecipeapp.data.mappers

import com.burakkodaloglu.foodrecipeapp.data.model.MealListDto
import com.burakkodaloglu.foodrecipeapp.domain.entities.Meal

fun MealListDto.toMeal(): Meal {
    return Meal(
        idMeal = idMeal ?: "",
        strMeal = strMeal ?: "",
        strMealThumb = strMealThumb ?: ""
    )
}

fun Meal.toMealListDto(): MealListDto {
    return MealListDto(
        idMeal = idMeal ?: "",
        strMeal = strMeal ?: "",
        strMealThumb = strMealThumb ?: ""
    )
}
