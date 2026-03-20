package com.example.chatapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.chatapp.ui.screens.LoginScreen
import com.example.chatapp.ui.screens.ChatScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        //FCM Token
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            println("FCM TOKEN: $it")
        }

        setContent {
            ChatAppTheme {
                //Auto login
                var isLoggedIn by remember {
                    mutableStateOf(FirebaseAuth.getInstance().currentUser != null)
                }

                val user = FirebaseAuth.getInstance().currentUser

                LaunchedEffect(user) {
                    Log.d("CHAT_APP", "UID: ${user?.uid}")
                    Log.d("CHAT_APP", "EMAIL: ${user?.email}")
                }



                if (isLoggedIn) {
                    ChatScreen(
                        onLogout = {
                            isLoggedIn = false
                        }
                    )
                } else {
                    LoginScreen {
                        isLoggedIn = true
                    }
                }
            }
        }
    }
}