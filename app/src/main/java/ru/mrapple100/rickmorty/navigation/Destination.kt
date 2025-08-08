package ru.mrapple100.rickmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import ru.mrapple100.rickmorty.ui.screen.Screen

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    WIKI(Screen.List.route, "Wiki", Icons.Default.DateRange, "Wiki"),
    GAME(Screen.Game.route, "Game", Icons.Default.Add, "Game"),
}