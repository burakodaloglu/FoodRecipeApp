package com.burakkodaloglu.foodrecipeapp.app.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object SignIn : Screens("sign_in_screen")
    object SignUp : Screens("signup_screen")
    object Home : Screens("home_screen")
}