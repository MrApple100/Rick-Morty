package ru.mrapple100.rickmorty.ui.pages.gamecharacters

sealed class GameCharactersSideEffect {
    class ChangeCharacters: GameCharactersSideEffect()
    class ShowWinStatus: GameCharactersSideEffect()
    class ShowLoseStatus: GameCharactersSideEffect()
}