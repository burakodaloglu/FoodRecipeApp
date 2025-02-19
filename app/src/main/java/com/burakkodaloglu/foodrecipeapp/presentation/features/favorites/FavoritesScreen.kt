package com.burakkodaloglu.foodrecipeapp.presentation.features.favorites

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.burakkodaloglu.foodrecipeapp.presentation.common.theme.FoodRecipeAppTheme

@Composable
fun FavoritesScreen() {
    Text("Favorites Screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodRecipeAppTheme {
        FavoritesScreen()
    }
}