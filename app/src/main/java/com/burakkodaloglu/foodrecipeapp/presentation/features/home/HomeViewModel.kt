package com.burakkodaloglu.foodrecipeapp.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakkodaloglu.foodrecipeapp.domain.repository.RecipeRepository
import com.burakkodaloglu.foodrecipeapp.presentation.common.Resource
import com.burakkodaloglu.foodrecipeapp.presentation.features.home.categories.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private var _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

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
                                errorMessage = resource.message ?: "Beklenmeyen hata"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _categoryState.update { it.copy(isLoading = resource.isLoading) }
                    }

                    is Resource.Success -> {
                        resource.data?.let { categories ->
                            _categoryState.update {
                                it.copy(
                                    categoryList = categories, // shuffled yerine direkt atama yapın
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}