package com.example.thesis_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BlueGreen,
    secondary = Slime,
    tertiary = DarkGreen
)

private val LightColorScheme = lightColorScheme(
    primary = BlueGreen,
    secondary = Slime,
    tertiary = DarkGreen
)

@Composable
fun THESIS_APPTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
