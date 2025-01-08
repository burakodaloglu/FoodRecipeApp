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
import com.burakkodaloglu.foodrecipeapp.presentation.features.home.HomeScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.splash.SplashScreen
import com.burakkodaloglu.foodrecipeapp.presentation.features.splash.SplashViewModel

sealed class NavRoute(val route: String) {
    object Splash : NavRoute("splash")
    object SignUp : NavRoute("signUp")
    object SignIn : NavRoute("signIn")
    object Home : NavRoute("home")
}

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {

        // Splash Screen
        composable(NavRoute.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect

            SplashScreen(
                uiEffect = uiEffect,
                onNavigateToLoginScreen = {
                    navController.navigate(NavRoute.SignIn.route) {
                        popUpTo(NavRoute.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToMainScreen = {
                    navController.navigate(NavRoute.Home.route) {
                        popUpTo(NavRoute.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        composable(NavRoute.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            SignUpScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToMain = {
                    navController.navigate(NavRoute.Home.route) {
                        popUpTo(NavRoute.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(NavRoute.SignIn.route) {
                        popUpTo(NavRoute.SignUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.SignIn.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignInScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                onNavigateToMain = {
                    navController.navigate(NavRoute.Home.route) {
                        popUpTo(NavRoute.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(NavRoute.SignUp.route) {
                        popUpTo(NavRoute.SignIn.route) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable(NavRoute.Home.route) {
            HomeScreen()
        }
    }
}
