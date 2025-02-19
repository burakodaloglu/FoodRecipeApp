package com.burakkodaloglu.foodrecipeapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.burakkodaloglu.foodrecipeapp.app.navigation.BottomNavigationBar
import com.burakkodaloglu.foodrecipeapp.app.navigation.NavGraph
import com.burakkodaloglu.foodrecipeapp.app.navigation.Screens
import com.burakkodaloglu.foodrecipeapp.presentation.common.theme.FoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodRecipeAppTheme {
                val navController = rememberNavController()
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                when (navBackStackEntry?.destination?.route) {
                    Screens.Splash.route, Screens.SignUp.route,Screens.SignIn.route -> bottomBarState.value = false
                    else -> bottomBarState.value = true
                }
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController,bottomBarState) }
                ) {
                    NavGraph(navController = navController,paddingValues = it)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodRecipeAppTheme {
       MainActivity()
    }
}