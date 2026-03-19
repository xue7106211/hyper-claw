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

val warmRose = Color(0xFFC27867)
val softPeach = Color(0xFFE8A598)

val DarkColorScheme = darkColorScheme(
    primary = softPeach,
    onPrimary = Color(0xFF1A1210),
    surface = Color(0xFF2A2220),
    background = Color(0xFF1C1614),
    onBackground = Color(0xFFFFF0EC),
    onSurface = Color(0xFFFFF0EC),
)

val LightColorScheme = lightColorScheme(
    primary = warmRose,
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFBF0ED),
    background = Color(0xFFFFF8F6),
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
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
