package com.burakkodaloglu.foodrecipeapp.data.source.remote

import com.burakkodaloglu.foodrecipeapp.data.model.CategoryListDto
import retrofit2.http.GET

interface RecipeService {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategoryList(): CategoryListDto

}