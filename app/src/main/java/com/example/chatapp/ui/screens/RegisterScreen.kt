package com.example.chatapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

import com.example.chatapp.ui.components.AppTextField
import com.example.chatapp.ui.components.AppButton
import com.example.chatapp.ui.components.SocialLoginRow
import com.example.chatapp.data.FirebaseRepository

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text("Your Logo")

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sign in up",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text("Lorem Ipsum is simply")

        Spacer(modifier = Modifier.height(20.dp))

        AppTextField(email, { email = it }, "Enter Email")

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(username, { username = it }, "Create User name")

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(phone, { phone = it }, "Contact number")

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(password, { password = it }, "Password", true)

        Spacer(modifier = Modifier.height(12.dp))

        AppTextField(confirmPassword, { confirmPassword = it }, "Confirm Password", true)

        Spacer(modifier = Modifier.height(24.dp))

        AppButton("Register") {

            if (password == confirmPassword) {
                FirebaseRepository.register(email, password)
                onRegisterSuccess()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("or continue with")

        Spacer(modifier = Modifier.height(16.dp))

        SocialLoginRow()

        Spacer(modifier = Modifier.height(20.dp))
    }
}