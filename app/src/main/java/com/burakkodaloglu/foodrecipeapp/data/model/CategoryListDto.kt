package com.burakkodaloglu.foodrecipeapp.data.model

import com.google.gson.annotations.SerializedName

data class CategoryListDto(
    @SerializedName("categories")
    val categories: List<CategoryDto>
)