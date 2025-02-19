package com.burakkodaloglu.foodrecipeapp.app.navigation

import com.burakkodaloglu.foodrecipeapp.R

sealed class BottomNavItem(
    val title: String,
    val image: Int,
    val route: String
) {
    object Home : BottomNavItem(
        title = "Home",
        image = R.drawable.ic_home,
        route = Screens.Home.route
    )

    object Favorites : BottomNavItem(
        title = "Favorites",
        image = R.drawable.ic_favorite,
        route = Screens.Favorites.route
    )
}