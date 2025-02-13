package com.burakkodaloglu.foodrecipeapp.data.repository

import com.burakkodaloglu.foodrecipeapp.data.mappers.toCategory
import com.burakkodaloglu.foodrecipeapp.data.source.remote.RecipeService
import com.burakkodaloglu.foodrecipeapp.domain.entities.Category
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
}
