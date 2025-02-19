package com.burakkodaloglu.foodrecipeapp.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn.SignInViewModel
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp.SignUpViewModel
import com.burakkodaloglu.foodrecipeapp.presentation.features.favorites.FavoritesScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.home.HomeScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.splash.SplashScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.splash.SplashViewModel


@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {

        // Splash Screen
        composable(Screens.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect

            SplashScreen(
                uiEffect = uiEffect,
                onNavigateToLoginScreen = {
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToMainScreen = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screens.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            SignUpScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToMain = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.SignUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screens.SignIn.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignInScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToMain = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screens.SignUp.route) {
                        popUpTo(Screens.SignIn.route) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable(Screens.Home.route) {
            HomeScreen()
        }
        composable(Screens.Favorites.route) {
            FavoritesScreen()
        }
    }
}
