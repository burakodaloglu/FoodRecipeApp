package com.burakkodaloglu.foodrecipeapp.data.mappers

import com.burakkodaloglu.foodrecipeapp.data.model.CategoryDto
import com.burakkodaloglu.foodrecipeapp.domain.entities.Category

fun CategoryDto.toCategory(): Category {
    return Category(
        idCategory = idCategory ?: "",
        strCategory = strCategory ?: "",
        strCategoryDescription = strCategoryDescription ?: "",
        strCategoryThumb = strCategoryThumb ?: ""
    )
}

fun Category.toCategoryDto(): CategoryDto {
    return CategoryDto(
        idCategory = this.idCategory,
        strCategory = this.strCategory,
        strCategoryDescription = this.strCategoryDescription,
        strCategoryThumb = this.strCategoryThumb
    )
}
