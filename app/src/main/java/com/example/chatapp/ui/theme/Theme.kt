package com.example.chatapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Primary,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun ChatAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}