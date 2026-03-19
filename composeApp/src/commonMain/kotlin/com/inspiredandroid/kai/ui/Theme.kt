@file:Suppress("DEPRECATION")

package com.inspiredandroid.kai.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

val brandBlue = Color(0xFF3366FF)
val lightBlue = Color(0xFF6B8FFF)

val DarkColorScheme = darkColorScheme(
    primary = lightBlue,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1A3AD4),
    onPrimaryContainer = Color(0xFFD9E0FF),
    secondary = Color(0xFF8A9BFF),
    onSecondary = Color(0xFF0A1A5C),
    secondaryContainer = Color(0xFF2A3A6E),
    onSecondaryContainer = Color(0xFFD9E0FF),
    tertiary = Color(0xFF8A9BFF),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF2A3A6E),
    onTertiaryContainer = Color(0xFFD9E0FF),
    surface = Color(0xFF1E2024),
    onSurface = Color(0xFFE8EAED),
    surfaceVariant = Color(0xFF2A2D33),
    onSurfaceVariant = Color(0xFFC4C7CE),
    background = Color(0xFF121316),
    onBackground = Color(0xFFE8EAED),
    outline = Color(0xFF44474E),
    outlineVariant = Color(0xFF2E3138),
)

val LightColorScheme = lightColorScheme(
    primary = brandBlue,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD9E0FF),
    onPrimaryContainer = Color(0xFF0A1A5C),
    secondary = Color(0xFF3D5BCC),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD9E0FF),
    onSecondaryContainer = Color(0xFF0A1A5C),
    tertiary = Color(0xFF3D5BCC),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD9E0FF),
    onTertiaryContainer = Color(0xFF0A1A5C),
    surface = Color(0xFFF1F3F8),
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFE2E5ED),
    onSurfaceVariant = Color(0xFF44474E),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1A1A1A),
    outline = Color(0xFF74777F),
    outlineVariant = Color(0xFFC4C7CE),
)

@Composable
fun outlineTextFieldColors() = OutlinedTextFieldDefaults.colors()

@Composable
@Preview
fun Theme(
    colorScheme: ColorScheme,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        content()
    }
}
