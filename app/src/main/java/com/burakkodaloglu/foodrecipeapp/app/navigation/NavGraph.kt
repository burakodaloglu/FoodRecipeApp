package com.burakkodaloglu.foodrecipeapp.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(
                navigateToSignUpScreen = {
                    navController.navigate(Screens.SignUp.route) {
                        popUpTo(Screens.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screens.SignUp.route) {
            SignUpScreen()
        }
        composable(route = Screens.SignIn.route) {
            SignInScreen()
        }
    }
}