package ru.mrapple100.rickmorty

import ru.mrapple100.rickmorty.navigation.Destination
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.GameCharactersSideEffect
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.ChooseUser

sealed class MainNavigationSideEffect {
    data class ChangeBottomDestination(val destination: Destination): MainNavigationSideEffect()
}