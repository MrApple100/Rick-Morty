package ru.mrapple100.rickmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    WIKI("", "Songs", Icons.Default.DateRange, "Wiki"),
    GAME("album", "Album", Icons.Default.Add, "Game"),
}