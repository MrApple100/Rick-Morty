package ru.mrapple100.rickmorty.ui.screen

import androidx.navigation.NavBackStackEntry

sealed class Screen(val route: String) {
    object List : Screen(route = "list")
    object Details : Screen(route = "details/{id}") {
        fun createRoute(id: Int) = "details/$id"
        fun getArgumentId(entry: NavBackStackEntry): Int {
            return entry.arguments?.getString("id")?.toInt() ?: 0
        }
    }
}