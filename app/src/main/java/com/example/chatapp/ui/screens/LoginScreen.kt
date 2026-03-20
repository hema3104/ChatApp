package com.example.chatapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.chatapp.ui.components.*
import com.example.chatapp.data.FirebaseRepository

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text("Your Logo")

        Spacer(modifier = Modifier.height(20.dp))

        Text("Sign in to", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Lorem Ipsum is simply")

        Spacer(modifier = Modifier.height(20.dp))

        AppTextField(email, { email = it }, "Enter email or username")

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(password, { password = it }, "Password", true)

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 SHOW ERROR IF ANY
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // 🔥 LOADING BUTTON
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            AppButton("Login") {

                // basic validation
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Please enter email & password"
                    return@AppButton
                }

                isLoading = true
                errorMessage = ""

                FirebaseRepository.login(email, password) { success ->

                    isLoading = false

                    if (success) {
                        onLoginSuccess()
                    } else {
                        errorMessage = "Invalid credentials"
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("or continue with")

        Spacer(modifier = Modifier.height(16.dp))

        SocialLoginRow()
    }
}