package com.burakkodaloglu.foodrecipeapp.presentation.features.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.burakkodaloglu.foodrecipeapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    uiEffect: kotlinx.coroutines.flow.Flow<SplashUiEffect>,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(uiEffect) {
        startAnimation = true
        delay(2000)
        uiEffect.collect { effect ->
            when (effect) {
                is SplashUiEffect.NavigateToHome -> onNavigateToMainScreen()
                is SplashUiEffect.NavigateToLogin -> onNavigateToLoginScreen()
                else -> {}
            }
        }
    }

    SplashDesign(alpha = alphaAnimation.value)
}

@Composable
fun SplashDesign(alpha: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(300.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.recipes_logo),
            contentDescription = stringResource(R.string.desc_logo),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
