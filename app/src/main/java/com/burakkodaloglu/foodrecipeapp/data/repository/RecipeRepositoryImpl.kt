package com.burakkodaloglu.foodrecipeapp.data.repository

import com.burakkodaloglu.foodrecipeapp.data.mappers.toCategory
import com.burakkodaloglu.foodrecipeapp.data.mappers.toMeal
import com.burakkodaloglu.foodrecipeapp.data.source.remote.RecipeService
import com.burakkodaloglu.foodrecipeapp.domain.entities.Category
import com.burakkodaloglu.foodrecipeapp.domain.entities.Meal
import com.burakkodaloglu.foodrecipeapp.domain.repository.RecipeRepository
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {

    override suspend fun getCategoryList(): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val categoryListFromApi = recipeService.getCategoryList()
                emit(Resource.Success(data = categoryListFromApi.categories.map { it.toCategory() }))
            } catch (e: Exception) {
                emit(Resource.Error("Error: ${e.localizedMessage}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getRecipeList(category: String): Flow<Resource<List<Meal>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val recipeListFromApi = recipeService.getRecipeList(category)
                emit(Resource.Success(data = recipeListFromApi.meals.map { it.toMeal() }))
            } catch (e: Exception) {
                emit(Resource.Error("Error: ${e.localizedMessage}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }
}
