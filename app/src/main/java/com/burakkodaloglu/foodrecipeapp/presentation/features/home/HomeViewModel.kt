package com.burakkodaloglu.foodrecipeapp.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.foodrecipeapp.domain.repository.RecipeRepository
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import com.burakkodaloglu.foodrecipeapp.presentation.features.home.categories.CategoryState
import com.burakkodaloglu.foodrecipeapp.presentation.features.home.meal.MealListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {

    private var _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

    private val _mealListState = MutableStateFlow(MealListState())
    val mealListState = _mealListState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            _categoryState.update { it.copy(isLoading = true) }
            recipeRepository.getCategoryList().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _categoryState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.message ?: "Unknown Error"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _categoryState.update { it.copy(isLoading = resource.isLoading) }
                    }
                    is Resource.Success -> {
                        resource.data?.let { categories ->
                            _categoryState.update { it.copy(categoryList = categories, isLoading = false) }
                        }
                    }
                }
            }
        }
    }

    fun getMealList(category: String) {
        _selectedCategory.value = category
        viewModelScope.launch {
            _mealListState.update { it.copy(isLoading = true) }
            recipeRepository.getRecipeList(category).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _mealListState.update {
                            it.copy(isLoading = false, errorMessage = resource.message)
                        }
                    }
                    is Resource.Loading -> {
                        _mealListState.update { it.copy(isLoading = resource.isLoading) }
                    }
                    is Resource.Success -> {
                        _mealListState.update {
                            it.copy(isLoading = false, mealList = resource.data ?: emptyList())
                        }
                    }
                }
            }
        }
    }
}