package ru.mrapple100.rickmorty.ui.screen

import androidx.navigation.NavBackStackEntry

sealed class Screen(val route: String) {
    object List : Screen(route = "list")
    object Details : Screen(route = "details?characterId={characterId}") {
        fun createRoute(characterId: Int) = "details?characterId=${characterId}"
        fun getArgumentId(entry: NavBackStackEntry): Int {
            return entry.arguments?.getString("characterId")?.toInt() ?: 0
        }
    }
}