package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.burakkodaloglu.foodrecipeapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignInScreen(
    uiState: SignInContract.UiState,
    uiEffect: Flow<SignInContract.UiEffect>,
    onAction: (SignInContract.UiAction) -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SignInContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignInContract.UiEffect.GoToMainScreen -> {
                        onNavigateToMain()
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        InputFieldsSection(
            email = uiState.email,
            password = uiState.password,
            onEmailChange = { onAction(SignInContract.UiAction.ChangeEmail(it)) },
            onPasswordChange = { onAction(SignInContract.UiAction.ChangePassword(it)) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        SignInButton(onClick = { onAction(SignInContract.UiAction.SignInClick) })
        Spacer(modifier = Modifier.height(16.dp))
        DividerWithText(text = "Or Sign In With")
        Spacer(modifier = Modifier.height(16.dp))
        GoogleSignInButton(onClick = { })
        Spacer(modifier = Modifier.height(16.dp))
        NavigateToSignUpText(onNavigateToSignUp = onNavigateToSignUp)
    }
}

@Composable
fun HeaderSection() {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Welcome Back!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign in to continue",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun InputFieldsSection(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF61353))
    ) {
        Text(text = "Sign In", fontSize = 16.sp, color = Color.White)
    }
}

@Composable
fun DividerWithText(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = TextStyle(fontSize = 14.sp, color = Color.LightGray)
        )
        Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign In with Google",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun NavigateToSignUpText(onNavigateToSignUp: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Don't have an account? ")
            withStyle(style = SpanStyle(color = Color(0xFFFFA500))) {
                append("Sign Up")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToSignUp() },
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        uiState = SignInContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateToSignUp = {},
        onNavigateToMain = {}
    )
}
