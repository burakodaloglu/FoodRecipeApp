package com.burakkodaloglu.foodrecipeapp.domain.repository

import com.burakkodaloglu.foodrecipeapp.domain.entities.Category
import com.burakkodaloglu.foodrecipeapp.domain.entities.Meal
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getCategoryList(): Flow<Resource<List<Category>>>
    suspend fun getRecipeList(category: String): Flow<Resource<List<Meal>>>

}