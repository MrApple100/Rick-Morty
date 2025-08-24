package ru.mrapple100.rickmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xff212121),
    onPrimary = Color(0xFFD2D2D2),
    primaryContainer = Color(0xFF373737),
    onPrimaryContainer = Color(0xFF7E7E7E),
    secondary = Color(0xFF424242),
    onSecondary = Color(0xFF141A3A),
    outline = Colors.greenWin,
    secondaryContainer = Color(0xff212121),
    error = Colors.redLose,
    onSurface = Color(0xFFFFFFFF)

)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFFD2D2D2),
    onPrimary = Color(0xff212121),
    primaryContainer = Color(0xFFC8C8C8),
    onPrimaryContainer = Color(0xFF7E7E7E),
    secondary = Color(0xFFB4B4B4),
    onSecondary = Color(0xFF141A3A),
    outline = Colors.greenWin,
    secondaryContainer = Color(0xff212121),
    error = Colors.redLose,
    onSurface = Color(0xff000000)

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