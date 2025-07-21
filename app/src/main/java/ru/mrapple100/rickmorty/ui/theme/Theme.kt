package ru.mrapple100.rickmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xff4c8c4a),
    primaryContainer = Color(0xff000000),
    secondary = Color(0xff212121),
    secondaryContainer = Color(0xff212121),
    onPrimary = Color(0xffffffff)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xff4c8c4a),
    primaryContainer = Color(0xff4c8c4a),
    secondary = Color(0xff212121),
    secondaryContainer = Color(0xff212121),
    onPrimary = Color(0xffffffff)
)

@Composable
fun RickAndMortyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}