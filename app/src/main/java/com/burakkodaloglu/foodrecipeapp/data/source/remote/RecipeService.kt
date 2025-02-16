package com.burakkodaloglu.foodrecipeapp.data.source.remote

import com.burakkodaloglu.foodrecipeapp.data.model.CategoryListDto
import com.burakkodaloglu.foodrecipeapp.data.model.RecipeListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategoryList(): CategoryListDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getRecipeList(@Query("c") category: String): RecipeListDto
}