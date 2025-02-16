package com.burakkodaloglu.foodrecipeapp.data.model

import com.google.gson.annotations.SerializedName

data class RecipeListDto(
    @SerializedName("meals")
    val meals: List<MealListDto>
)