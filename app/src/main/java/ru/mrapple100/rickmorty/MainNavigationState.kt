package ru.mrapple100.rickmorty

import ru.mrapple100.rickmorty.navigation.Destination

data class MainNavigationState(
    val selectedDestinationBottom: Destination = Destination.WIKI
)