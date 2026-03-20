package com.example.chatapp.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.chatapp.R

@Composable
fun SocialLoginRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(painterResource(R.drawable.ic_facebook), contentDescription = "")
        Image(painterResource(R.drawable.ic_apple), contentDescription = "")
        Image(painterResource(R.drawable.ic_google), contentDescription = "")
    }
}
