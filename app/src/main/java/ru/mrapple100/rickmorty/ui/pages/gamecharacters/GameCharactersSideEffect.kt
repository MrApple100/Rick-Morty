package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.ChooseUser

sealed class GameCharactersSideEffect {
    class ToEndOnBoarding: GameCharactersSideEffect()
    class ToStartOnBoarding : GameCharactersSideEffect()
    data class ShowStatus(val chooseUser: ChooseUser): GameCharactersSideEffect()
}