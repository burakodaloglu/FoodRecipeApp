package com.burakkodaloglu.foodrecipeapp.presentation.features.auth.signUp

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
import androidx.compose.ui.text.font.FontStyle
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
fun SignUpScreen(
    uiState: SignUpContract.UiState,
    uiEffect: Flow<SignUpContract.UiEffect>,
    onAction: (SignUpContract.UiAction) -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    //onGoogleSignUp: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { effect ->
                when (effect) {
                    is SignUpContract.UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignUpContract.UiEffect.GoToSignIn -> {
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
            name = uiState.name,
            email = uiState.email,
            password = uiState.password,
            //confirmPassword = uiState.confirmPassword,
            onNameChange = { onAction(SignUpContract.UiAction.ChangeName(it)) },
            onEmailChange = { onAction(SignUpContract.UiAction.ChangeEmail(it)) },
            onPasswordChange = { onAction(SignUpContract.UiAction.ChangePassword(it)) },
            //onConfirmPasswordChange = { onAction(SignUpContract.UiAction.ChangeConfirmPassword(it)) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        SignUpButton(onClick = { onAction(SignUpContract.UiAction.SignUpClick) })
        Spacer(modifier = Modifier.height(16.dp))
        DividerWithText(text = "Or Sign Up With")
        Spacer(modifier = Modifier.height(16.dp))
        GoogleSignUpButton(onClick = { }) // Google giriş aksiyonu bağlandı
        Spacer(modifier = Modifier.height(16.dp))
        NavigateToLoginText(onNavigateToLogin = onNavigateToSignIn) // Girişe yönlendirme bağlandı
    }
}


@Composable
fun HeaderSection() {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Create an Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Let's help you set your account,",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        )
        Text(
            text = "it won't take long.",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        )
    }
}

@Composable
fun InputFieldsSection(
    name: String,
    email: String,
    password: String,
    //confirmPassword: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    //onConfirmPasswordChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
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
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = "",
        onValueChange = {  },
        label = { Text("Confirm Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}



@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF61353))
    ) {
        Text(text = "Sign Up", fontSize = 16.sp, color = Color.White)
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
fun GoogleSignUpButton(onClick: () -> Unit) {
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
                text = "Sign Up with Google",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}


@Composable
fun NavigateToLoginText(onNavigateToLogin: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Already a member? ")
            withStyle(style = SpanStyle(color = Color(0xFFFFA500))) {
                append("Sign In")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToLogin() },
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        uiState = SignUpContract.UiState(),
        uiEffect = emptyFlow(),
        onAction = {},
        onNavigateToSignIn = {},
        onNavigateToMain = {},
    )
}
